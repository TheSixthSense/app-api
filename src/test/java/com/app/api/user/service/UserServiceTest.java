package com.app.api.user.service;

import com.app.api.config.BaseTest;
import com.app.api.core.exception.BizException;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.entity.User;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.enums.VegannerStage;
import com.app.api.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("User Test")
class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @Order(1)
    @Transactional
    @DisplayName("회원가입")
    class SignUp {
        protected UserRegDTO userRegDTO;
        @BeforeEach
        void beforeEach() {
            this.userRegDTO = UserRegDTO.builder()
                    .appleId(appleId)
                    .clientSecret(clientSecret)
                    .gender(gender)
                    .birthDay(birthDay)
                    .userRoleType(userRoleType)
                    .nickName(nickName)
                    .vegannerStage(vegannerStage)
                    .build();
        }

        @Test()
        @Order(1)
        @Transactional
        @DisplayName("정상 회원가입")
        void signup() {
            // given - userRegDTO

            // when & then
            userService.signup(userRegDTO);
            assert true;
        }

        @Test()
        @Order(2)
        @Transactional
        @DisplayName("client secret 값 이상")
        void error_when_client_secret_value_is_wrong() {
            // given
            userRegDTO.setClientSecret("wrongToken");

            // when & then
            assertThrows(BizException.class,
                    () -> userService.signup(userRegDTO),
                    messageComponent.getMessage("exception.wrong.value.client.secret"));
        }

        @Test()
        @Order(3)
        @Transactional
        @DisplayName("어드민 권한 생성불가")
        void error_when_create_admin_role() {
            // given
            userRegDTO.setUserRoleType(UserRoleType.ADMIN);

            // when & then
            assertThrows(BizException.class,
                    () -> userService.signup(userRegDTO),
                    messageComponent.getMessage("exception.user.create.admin.role"));
        }

        @Test()
        @Order(4)
        @Transactional
        @DisplayName("중복된 email로 아이디 생성 불가")
        void error_when_email_is_duplicated() {
            // given
            UserRegDTO userRegDTO2 = new UserRegDTO();
            userRegDTO2.setAppleId("OOOOOOO1");
            userRegDTO2.setClientSecret(clientSecret);
            userRegDTO2.setGender(Gender.MALE);
            userRegDTO2.setBirthDay("19920910");
            userRegDTO2.setUserRoleType(UserRoleType.USER);
            userRegDTO2.setNickName("testNickName2");
            userRegDTO2.setVegannerStage(VegannerStage.BEGINNER);

            assertThrows(BizException.class,
                    () -> {
                        userService.signup(userRegDTO);
                        userService.signup(userRegDTO2);
                    },
                    messageComponent.getMessage("exception.user.already.exist"));
        }

        @Test()
        @Order(5)
        @Transactional
        @DisplayName("중복된 nickName 사용 불가")
        void error_when_nick_name_is_duplicated() {
            // given
            UserRegDTO userRegDTO2 = new UserRegDTO();
            userRegDTO2.setAppleId(appleId);
            userRegDTO2.setClientSecret("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJ0ZXN0QGdvb2dsZS5jb20iLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE1MTYyMzkwMjJ9.ZQ7diVSCBsZpPc5X7Z70mt4yEUGSZm0ctdWlO3tpL30");
            userRegDTO2.setGender(gender);
            userRegDTO2.setBirthDay(birthDay);
            userRegDTO2.setUserRoleType(userRoleType);
            userRegDTO2.setNickName(nickName);
            userRegDTO2.setVegannerStage(VegannerStage.BEGINNER);

            assertThrows(BizException.class,
                    () -> {
                        userService.signup(userRegDTO);
                        userService.signup(userRegDTO2);
                    },
                    messageComponent.getMessage("exception.user.nickName.already.exist"));
        }

        @Test()
        @Order(6)
        @Transactional
        @DisplayName("토큰값에서 이메일을 찾을 수 없습니다.")
        void error_when_client_secret_do_not_containe_email() {
            // given
            userRegDTO.setClientSecret("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

            assertThrows(BizException.class,
                    () -> userService.signup(userRegDTO),
                    messageComponent.getMessage("exception.user.client.secret.is.not.contain.email"));
        }
    }

    @Nested
    @Transactional
    @Order(2)
    @DisplayName("닉네임 중복확인")
    class DuplicateNickName {

        @Test()
        @Order(1)
        @Transactional
        @DisplayName("중복된 nickName 사용 불가")
        void error_when_nick_name_is_duplicated() {
            // given
            userRepository.save(User.builder()
                    .appleId(appleId)
                    .email("test@gmail.com")
                    .gender(gender)
                    .birthDay(birthDay)
                    .userRoleType(userRoleType)
                    .nickName(nickName)
                    .vegannerStage(vegannerStage)
                    .build());

            // when & then
            assertThrows(BizException.class,
                    () -> userService.checkDuplicateNickname(nickName),
                    messageComponent.getMessage("exception.user.nickName.already.exist"));
        }
    }
}