package com.app.api.user.entity;

import com.app.api.common.entity.BaseTimeEntity;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.enums.VegannerStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name = "user_info")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String appleId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String birthDay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VegannerStage vegannerStage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType;
}
