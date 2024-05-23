package com.example.searchapi.repository;

import com.example.searchapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByTagNameIsIn(List<String> names);

}
