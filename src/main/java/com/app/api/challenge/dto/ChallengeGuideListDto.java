package com.app.api.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeGuideListDto {

    private long id;
    private long challengeId;
    private String title;
    private String description;
    private String imagePath;
    
}
