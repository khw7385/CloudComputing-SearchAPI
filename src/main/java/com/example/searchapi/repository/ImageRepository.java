package com.example.searchapi.repository;

import com.example.searchapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByUserIDIsAndImageIDLessThanEqual(BigDecimal UserID, Long imageID);
    List<Image> findAllByUserIDIsAndImageIDIsInOrderByUploadTimeDesc(BigDecimal UserID, List<Long> ImageID);
    List<Image> findAllByUserIDIsOrderByUploadTimeDesc(BigDecimal UserID);

    @Query("SELECT i.imageID FROM Image i WHERE i.userID = :UserID")
    List<Long> findAllImageIDByUserIDIs(BigDecimal UserID);

}
