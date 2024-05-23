package com.example.searchapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public class SearchImageDTO {

    @Builder
    public record Request(List<String> tags, Integer size, Long nextImageId) {

        public Command toCommand(BigDecimal userId) {
            return Command.builder()
                    .userId(userId)
                    .tags(tags)
                    .size(size)
                    .nextImageId(nextImageId)
                    .build();
        }
    }

    @Builder
    public record Command(BigDecimal userId, List<String> tags, Integer size, Long nextImageId) {

    }


    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Response(List<Long> imageIds, Boolean hasNext, Long nextImageId) {

    }


}
