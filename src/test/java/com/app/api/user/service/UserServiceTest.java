package com.app.api.user.service;

import com.app.api.config.BaseTest;
import com.app.api.core.exception.BizException;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.entity.User;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.enums.VegannerStage;
import com.app.api.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("User Test")
class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @Order(1)
    @DisplayName("회원가입")
    class SignUp {
        @Test()
        @Order(1)
        @DisplayName("정상 회원가입")
        void signup() {
            // given
            UserRegDTO userRegDTO = new UserRegDTO();
            userRegDTO.setAppleId("OOOOOOOO");
            userRegDTO.setClientSecret("eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw");
            userRegDTO.setGender(Gender.MALE);
            userRegDTO.setBirthDay("19920910");
            userRegDTO.setUserRoleType(UserRoleType.USER);
            userRegDTO.setNickName("testNickName");
            userRegDTO.setVegannerStage(VegannerStage.BEGINNER);

            userService.signup(userRegDTO);
            assert true;
        }

        @Test()
        @Order(2)
        @DisplayName("client secret 값 이상")
        void error_when_client_secret_value_is_wrong() {
            // given
            UserRegDTO userRegDTO = new UserRegDTO();
            userRegDTO.setAppleId("OOOOOOOO");
            userRegDTO.setClientSecret("sss");
            userRegDTO.setGender(Gender.MALE);
            userRegDTO.setBirthDay("19920910");
            userRegDTO.setUserRoleType(UserRoleType.USER);
            userRegDTO.setNickName("testNickName");
            userRegDTO.setVegannerStage(VegannerStage.BEGINNER);

            assertThrows(BizException.class,
                    () -> userService.signup(userRegDTO),
                    messageComponent.getMessage("exception.wrong.value.client.secret"));
        }

        @Test()
        @Order(3)
        @DisplayName("어드민 권한 생성불가")
        void error_when_create_admin_role() {
            // given
            UserRegDTO userRegDTO = new UserRegDTO();
            userRegDTO.setAppleId("OOOOOOOO");
            userRegDTO.setClientSecret("eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw");
            userRegDTO.setGender(Gender.MALE);
            userRegDTO.setBirthDay("19920910");
            userRegDTO.setUserRoleType(UserRoleType.ADMIN);
            userRegDTO.setNickName("testNickName");
            userRegDTO.setVegannerStage(VegannerStage.BEGINNER);

            assertThrows(BizException.class,
                    () -> userService.signup(userRegDTO),
                    messageComponent.getMessage("exception.user.create.admin.role"));
        }

        @Test()
        @Order(4)
        @DisplayName("중복된 email로 아이디 생성 불가")
        void error_when_email_is_duplicated() {
            // given
            UserRegDTO userRegDTO1 = new UserRegDTO();
            userRegDTO1.setAppleId("OOOOOOOO");
            userRegDTO1.setClientSecret("eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw");
            userRegDTO1.setGender(Gender.MALE);
            userRegDTO1.setBirthDay("19920910");
            userRegDTO1.setUserRoleType(UserRoleType.USER);
            userRegDTO1.setNickName("testNickName1");
            userRegDTO1.setVegannerStage(VegannerStage.BEGINNER);

            UserRegDTO userRegDTO2 = new UserRegDTO();
            userRegDTO2.setAppleId("OOOOOOO1");
            userRegDTO2.setClientSecret("eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw");
            userRegDTO2.setGender(Gender.MALE);
            userRegDTO2.setBirthDay("19920910");
            userRegDTO2.setUserRoleType(UserRoleType.USER);
            userRegDTO2.setNickName("testNickName2");
            userRegDTO2.setVegannerStage(VegannerStage.BEGINNER);

            assertThrows(BizException.class,
                    () -> {
                        userService.signup(userRegDTO1);
                        userService.signup(userRegDTO2);
                    },
                    messageComponent.getMessage("exception.user.already.exist"));
        }

        @Test()
        @Order(4)
        @DisplayName("중복된 nickName 사용 불가")
        void error_when_nick_name_is_duplicated() {
            // given
            UserRegDTO userRegDTO1 = new UserRegDTO();
            userRegDTO1.setAppleId("OOOOOOOO");
            userRegDTO1.setClientSecret("eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw");
            userRegDTO1.setGender(Gender.MALE);
            userRegDTO1.setBirthDay("19920910");
            userRegDTO1.setUserRoleType(UserRoleType.USER);
            userRegDTO1.setNickName("testNickName");
            userRegDTO1.setVegannerStage(VegannerStage.BEGINNER);

            UserRegDTO userRegDTO2 = new UserRegDTO();
            userRegDTO2.setAppleId("OOOOOOO1");
            userRegDTO2.setClientSecret("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJ0ZXN0QGdvb2dsZS5jb20iLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ZQ7diVSCBsZpPc5X7Z70mt4yEUGSZm0ctdWlO3tpL30");
            userRegDTO2.setGender(Gender.MALE);
            userRegDTO2.setBirthDay("19920910");
            userRegDTO2.setUserRoleType(UserRoleType.USER);
            userRegDTO2.setNickName("testNickName");
            userRegDTO2.setVegannerStage(VegannerStage.BEGINNER);

            assertThrows(BizException.class,
                    () -> {
                        userService.signup(userRegDTO1);
                        userService.signup(userRegDTO2);
                    },
                    messageComponent.getMessage("exception.user.nickName.already.exist"));
        }

        @Test()
        @Order(5)
        @DisplayName("토큰값에서 이메일을 찾을 수 없습니다.")
        void error_when_client_secret_do_not_containe_email() {
            // given
            UserRegDTO userRegDTO = new UserRegDTO();
            userRegDTO.setAppleId("OOOOOOO1");
            userRegDTO.setClientSecret("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
            userRegDTO.setGender(Gender.MALE);
            userRegDTO.setBirthDay("19920910");
            userRegDTO.setUserRoleType(UserRoleType.USER);
            userRegDTO.setNickName("testNickName");
            userRegDTO.setVegannerStage(VegannerStage.BEGINNER);

            assertThrows(BizException.class,
                    () -> userService.signup(userRegDTO),
                    messageComponent.getMessage("exception.user.client.secret.is.not.contain.email"));
        }
    }

    @Nested
    @Order(2)
    @DisplayName("닉네임 중복확인")
    class DuplicateNickName {

        @Test()
        @Order(1)
        @DisplayName("중복된 nickName 사용 불가")
        void error_when_nick_name_is_duplicated() {
            userRepository.save(User.builder()
                    .appleId("00000000")
                    .email("test@gmail.com")
                    .gender(Gender.MALE)
                    .birthDay("19920910")
                    .userRoleType(UserRoleType.USER)
                    .nickName("testNickName1")
                    .vegannerStage(VegannerStage.BEGINNER)
                    .build());

            String testNickName = "testNickName1";

            assertThrows(BizException.class,
                    () -> userService.checkDuplicateNickname(testNickName),
                    messageComponent.getMessage("exception.user.nickName.already.exist"));
        }
    }
}