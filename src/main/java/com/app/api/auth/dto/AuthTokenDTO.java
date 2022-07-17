package com.app.api.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class AuthTokenDTO {

    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;
}
