package com.example.searchapi.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Entity(name = "ImageTag")
public class ImageHasTags {

    public record PK(Long TagID, Long ImageID) implements Serializable {

    };

    @EmbeddedId
    private PK pk;

}
