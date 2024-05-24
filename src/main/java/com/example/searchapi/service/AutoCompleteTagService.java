package com.example.searchapi.service;

import com.example.searchapi.dto.AutoCompleteTagDTO;

public interface AutoCompleteTagService {

    AutoCompleteTagDTO.Response getAutoCompleteTags(AutoCompleteTagDTO.Command command);

}
