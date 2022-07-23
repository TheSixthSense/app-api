package com.app.api.jwt.dto;
import com.app.api.jwt.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    @NotNull
    private long userId;
    private Role role = Role.USER;

    public TokenDto(long userId) {
        this.userId = userId;
        this.role = Role.USER;
    }
}
