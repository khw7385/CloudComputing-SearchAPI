package com.example.searchapi.service;

import com.example.searchapi.dto.SearchImageDTO;

public interface SearchImageService {

    SearchImageDTO.Response searchByTags(SearchImageDTO.Command command);

}
