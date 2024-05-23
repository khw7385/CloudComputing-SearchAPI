package com.example.searchapi.repository;

import com.example.searchapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByTagNameIsIn(List<String> names);

    @Query("SELECT t.tagName FROM Tag t WHERE t.tagID IN :tagIDs")
    List<String> findAllTagNameByTagIDIsIn(Collection<Long> tagIDs);

}
