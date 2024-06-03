package com.example.searchapi.service.impl;

import com.example.searchapi.dto.SearchImageDTO;
import com.example.searchapi.entity.Image;
import com.example.searchapi.entity.Tag;
import com.example.searchapi.repository.ImageRepository;
import com.example.searchapi.repository.ImageTagAssociationRepository;
import com.example.searchapi.repository.TagRepository;
import com.example.searchapi.service.SearchImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchImageServiceImpl implements SearchImageService {

    private final ImageRepository imageRepository;
    private final ImageTagAssociationRepository associationRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public SearchImageDTO.Response searchByTags(SearchImageDTO.Command command) {
        final Long targetImageId = Optional.ofNullable(command.nextImageId()).orElse(Long.MAX_VALUE);
        final Timestamp targetTimestamp = imageRepository.findByUserIDIsAndImageIDLessThanEqual(command.userId(), targetImageId)
                .stream()
                .map(Image::getUploadTime)
                .max(Comparator.comparing(Timestamp::getTime))
                .orElse(Timestamp.valueOf(LocalDateTime.now()));
        final Integer searchSize = Integer.max(command.size() , 0); //size 버그 방지

        //tag 테이블로부터 키워드를 가진 tagID를 모두 가져옴
        //엔티티 연관관계 매핑으로 Join 후 쿼리를 한 번만 보내는 것이 좋을까..?
        List<Long> tagIDs = tagRepository.findAllByTagNameIsIn(command.tags()).stream()
                .map(Tag::getTagID)
                .toList();

        //TO-DO: 이미지 목록을 Redis에 캐싱함
        final List<Image> images;
        if (tagIDs.isEmpty()) {
            //태그가 없다면 유저의 모든 이미지를 불러옴
            images = imageRepository.findAllByUserIDIsOrderByUploadTimeDesc(command.userId());
        } else {
            //ImageTag 테이블로부터 tagID를 모두 가진 imageID만 가져옴
            List<Long> imageIDs = associationRepository.findAllImageIDsWithAllTagIDs(tagIDs, tagIDs.size());

            //Image 테이블로부터 사용자의 이미지이면서 ImageIDs에 포함되고 최신순으로 정렬한 image 목록을 가져옴
            images = imageRepository.findAllByUserIDIsAndImageIDIsInOrderByUploadTimeDesc(command.userId(), imageIDs);
        }

        //targetTimestamp보다 작거나 같은 이미지들 중 클라이언트가 보낸 size만큼 잘라서 저장함
        //TO-DO: 아래 stream 성능 개선, stream으로 조회되는 Image들의 중복이 좀 많음
        List<Image> resultImages = images.stream()
                .filter(image -> image.getUploadTime().before(targetTimestamp) || image.getUploadTime().equals(targetTimestamp))
                .limit(searchSize)
                .toList();

        //만약 검색된 이미지가 하나도 없다면 빈 리스트를 반환함
        if (resultImages.isEmpty()) {
            return SearchImageDTO.Response.builder()
                    .imageIds(Collections.emptyList())
                    .hasNext(false)
                    .build();
        }

        //선택된 이미지들 중 가장 오래 전에 저장된 uploadTime을 가져옴
        Timestamp minimumUploadTime = resultImages.stream()
                .map(Image::getUploadTime)
                .min(Comparator.comparing(Timestamp::getTime))
                .get();

        //minimumUploadTime보다 더 오래 전에 저장된 이미지들 중 하나를 골라 ImageID만 가져옴
        Long nextImageId = images.stream()
                .filter(image -> image.getUploadTime().before(minimumUploadTime))
                .limit(1)
                .map(Image::getImageID)
                .findFirst()
                .orElse(null);

        //nextImageId가 null인지 아닌지 저장함
        Boolean hasNext = nextImageId != null;

        //Response 객체에 모두 담아 반환
        return SearchImageDTO.Response.builder()
                .imageIds(resultImages.stream().map(Image::getImageID).toList())
                .hasNext(hasNext)
                .nextImageId(nextImageId)
                .build();
    }
}
