package com.app.api.jwt.dto;
import com.app.api.user.enums.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
public class TokenDto {
    @NotNull
    private long userId;
    private UserRoleType role = UserRoleType.USER;

    public TokenDto(long userId) {
        this.userId = userId;
    }

    public TokenDto(long userId, UserRoleType userRoleType) {
        this.userId = userId;
        this.role = userRoleType;
    }
}
