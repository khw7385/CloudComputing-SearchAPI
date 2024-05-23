package com.example.searchapi.service.impl;

import com.example.searchapi.dto.AutoCompleteTagDTO;
import com.example.searchapi.entity.Image;
import com.example.searchapi.entity.Tag;
import com.example.searchapi.exception.NotKoreanTagFoundException;
import com.example.searchapi.repository.ImageRepository;
import com.example.searchapi.repository.ImageTagAssociationRepository;
import com.example.searchapi.repository.TagRepository;
import com.example.searchapi.service.AutoCompleteTagService;
import com.example.searchapi.trie.Trie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AutoCompleteTagServiceImpl implements AutoCompleteTagService {

    private final ImageRepository imageRepository;
    private final ImageTagAssociationRepository associationRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public AutoCompleteTagDTO.Response getAutoCompleteTags(AutoCompleteTagDTO.Command command) {

        //사용자의 모든 이미지를 가져옴
        List<Long> imageIDs = imageRepository.findAllImageIDByUserIDIs(command.userId());

        //이미지가 가진 태그 목록 중 중복을 제거한 태그 ID를 가져옴
        Set<Long> uniqueTags = associationRepository.findDistinctTagIDByImageIDIsIn(imageIDs);

        //태그 ID에 해당하는 태그 이름을 가져옴
        List<String> tagNames = tagRepository.findAllTagNameByTagIDIsIn(uniqueTags);

        final List<String> autoCompleteTags;
        try {
            //Trie 자료구조 생성
            Trie trie = new Trie();
            trie.insertAll(tagNames);

            //Trie를 바탕으로 자동완성이 가능한 tag를 계산함
            autoCompleteTags = trie.searchTags(command.input());

        } catch (NotKoreanTagFoundException notFoundException) {
            //검색하고 있는 키워드가 빈 문자열일 때도 catch에 걸림
            //빈 문자열인 경우 모든 태그가 자동완성 대상이 되지만
            //클라이언트에게 보낼 데이터의 개수가 너무 많기 때문에
            //빈 문자열은 자동완성 태그를 하나도 보내지 않음
            return AutoCompleteTagDTO.Response.builder()
                    .suggestKeywords(Collections.emptyList())
                    .build();
        }

        return AutoCompleteTagDTO.Response.builder()
                .suggestKeywords(autoCompleteTags)
                .build();
    }
}
