package com.app.api.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class AuthLoginDTO {

    @ApiModelProperty(value = "Apple User Identifier", example = "OOOOOOOO")
    @NotBlank
    private String appleId;
}
