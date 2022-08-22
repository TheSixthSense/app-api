package com.app.api.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeListDto {

    private long id;
    private long categoryId;
    private String name;
    private String emoji;
    private String description;
    private Integer sort;
}
