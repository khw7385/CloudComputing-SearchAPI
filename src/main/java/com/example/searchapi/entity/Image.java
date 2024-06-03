package com.example.searchapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageID")
    private Long imageID;

    @Column(name = "UserID")
    private BigDecimal userID;

    @Column(name = "S3URL")
    private String s3Url;

    @CreationTimestamp
    @Column(name = "UploadTime")
    private Timestamp uploadTime;

}
