package com.app.api.user.dto;

import com.app.api.common.validator.ValueOfEnum;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.enums.VegannerStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class UserRegDTO {

    @ApiModelProperty(value = "Apple 계정 정보", example = "001805.7d48278a5f8d4c618263bef5a616f7dc.1512", required = true)
    @NotEmpty
    private String appleId;

    @ApiModelProperty(value = "Apple client secret", example = "eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw", required = true)
    @NotEmpty
    private String clientSecret;

    @ApiModelProperty(value = "닉네임", example = "비건챌린져_1", required = true)
    @NotEmpty
    private String nickName;

    @ApiModelProperty(value = "성별", example = "MALE", required = true)
    @ValueOfEnum(enumClass = Gender.class)
    private Gender gender;

    @ApiModelProperty(value = "생년월일", example = "19920910", required = true)
    @NotEmpty
    @Pattern(regexp = "\\d{8}")
    private String birthDay;

    @ApiModelProperty(value = "비건 실천이력", example = "BEGINNER", required = true)
    @ValueOfEnum(enumClass = VegannerStage.class)
    private VegannerStage vegannerStage;

    @ApiModelProperty(value = "계정 권한", example = "USER")
    @ValueOfEnum(enumClass = UserRoleType.class)
    private UserRoleType userRoleType;
}
