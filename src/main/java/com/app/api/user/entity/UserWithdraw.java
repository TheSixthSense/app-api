package com.app.api.user.entity;

import com.app.api.common.entity.BaseTimeEntity;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.enums.VegannerStage;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name = "user_withdraw")
public class UserWithdraw extends BaseTimeEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private String appleId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String birthDay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VegannerStage vegannerStage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType;
}
