package com.app.api.user.dto;

import com.app.api.user.enums.UserRoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class UserDTO {

    @ApiModelProperty(value = "id", example = "1")
    private Long id;

    @ApiModelProperty(value = "email", example = "vegganer@gmail.com")
    private String email;

    @ApiModelProperty(value = "계정이름", example = "비거너1_2")
    private String nickName;

    @ApiModelProperty(value = "계정권한", example = "USER")
    private UserRoleType userRoleType;

    @ApiModelProperty(value = "생성날짜", example = "2022-07-10T00:55:00.181448")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "수정날짜", example = "2022-07-10T00:55:00.181448")
    private LocalDateTime updatedDate;
}
