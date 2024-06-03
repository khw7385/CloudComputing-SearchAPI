package com.example.searchapi.repository;

import com.example.searchapi.entity.ImageHasTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ImageTagAssociationRepository extends JpaRepository<ImageHasTags, ImageHasTags.PK> {

    @Query("SELECT it.pk.ImageID FROM ImageTag it WHERE it.pk.TagID IN :tags GROUP BY it.pk.ImageID HAVING COUNT(DISTINCT it.pk.TagID) = :tagCount")
    List<Long> findAllImageIDsWithAllTagIDs(List<Long> tags, int tagCount);

    @Query("SELECT it.pk.TagID FROM ImageTag it WHERE it.pk.ImageID IN :images GROUP BY it.pk.TagID")
    Set<Long> findDistinctTagIDByImageIDIsIn(List<Long> images);

}
