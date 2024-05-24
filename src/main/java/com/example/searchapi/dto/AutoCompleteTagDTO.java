package com.example.searchapi.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public class AutoCompleteTagDTO {

    @Builder
    public record Request(String input) {

        public Command toCommand(BigDecimal userId) {
            return Command.builder()
                    .userId(userId)
                    .input(input)
                    .build();
        }
    }

    @Builder
    public record Command(BigDecimal userId, String input) {

    }

    @Builder
    public record Response(List<String> suggestKeywords) {

    }
}
