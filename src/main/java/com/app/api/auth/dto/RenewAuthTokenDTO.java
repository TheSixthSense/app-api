package com.app.api.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class RenewAuthTokenDTO {

    @ApiModelProperty(value = "appleId", example = "OOOOOOOO")
    @NotBlank
    private String appleId;

    @ApiModelProperty(value = "refreshToken", example = "before")
    @NotBlank
    private String refreshToken;
}
