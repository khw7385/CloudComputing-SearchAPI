package com.example.searchapi.repository;

import com.example.searchapi.entity.ImageHasTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageTagAssociationRepository extends JpaRepository<ImageHasTags, ImageHasTags.PK> {

    @Query("SELECT it.pk.ImageID FROM ImageTag it WHERE it.pk.TagID IN :tags GROUP BY it.pk.ImageID HAVING COUNT(DISTINCT it.pk.TagID) = :tagCount")
    List<Long> findAllImageIDsWithAllTagIDs(List<Long> tags, int tagCount);

}
