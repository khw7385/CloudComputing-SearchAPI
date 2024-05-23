package com.example.searchapi.controller;

import com.example.searchapi.dto.SearchImageDTO;
import com.example.searchapi.service.SearchImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchImageController {

    private final SearchImageService searchImageService;

    @GetMapping("/tag")
    public ResponseEntity<SearchImageDTO.Response> searchByTags(@RequestAttribute BigDecimal userId,
                                                                @ModelAttribute SearchImageDTO.Request request) {

        return ResponseEntity.ok(searchImageService.searchByTags(request.toCommand(userId)));
    }
}
