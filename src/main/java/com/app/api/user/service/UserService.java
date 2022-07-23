package com.app.api.user.service;

import com.app.api.core.exception.BizException;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.entity.User;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.api.common.util.jwt.JwtUtil.getEmailFromAppleSecretToken;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final Pattern EMAIL = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"); // effective java item 6 technique

    /**
     * 회원가입
     * 예외조건 : 기존 email이 존재하는 경우
     */
    @Transactional
    public void signup(UserRegDTO userRegDTO) {
        // 권한 입력값이 없을 경우 기본적으로 USER 할당
        if (userRegDTO.getUserRoleType() == null) {
            userRegDTO.setUserRoleType(UserRoleType.USER);
        }
        // 생성권한 체크
        checkUserRoleType(userRegDTO);

        // extract email from Apple secretToken
        String email = getEmailFromAppleSecretToken(userRegDTO.getClientSecret());

        // check email form
        checkEmailForm(email);

        // check duplicated nickName
        checkDuplicateNickname(userRegDTO.getNickName());

        // 기존 회원가입 여부 확인 - email을 기준
        userRepository.findByEmail(email).ifPresent(user -> {
            throw BizException.withUserMessageKey("exception.user.already.exist").build();
        });

        // save user
        userRepository.save(User.builder()
                .appleId(userRegDTO.getAppleId())
                .email(email)
                .nickName(userRegDTO.getNickName())
                .gender(userRegDTO.getGender())
                .birthDay(userRegDTO.getBirthDay())
                .vegannerStage(userRegDTO.getVegannerStage())
                .userRoleType(userRegDTO.getUserRoleType())
                .build());
    }

    /**
     * 닉네임 중복여부 확인
     */
    public void checkDuplicateNickname(String nickName) {
        userRepository.findByNickName(nickName).ifPresent(user -> {
            throw BizException.withUserMessageKey("exception.user.nickName.already.exist").build();
        });
    }

    /**
     * 어드민 권한 생성불가(관리자에게 문의)
     */
    private void checkUserRoleType(UserRegDTO userRegDTO) {
        if (userRegDTO.getUserRoleType() == null) {
            throw BizException.withUserMessageKey("exception.user.role.type.null").build();
        }

        if (userRegDTO.getUserRoleType() == UserRoleType.ADMIN) {
            throw BizException.withUserMessageKey("exception.user.create.admin.role").build();
        }
    }



    /**
     * 이메일 형식 체크
     */
    private void checkEmailForm(String email) {
        Matcher m = EMAIL.matcher(email);
        if (!m.matches()) {
            throw BizException.withUserMessageKey("exception.common.email.form").build();
        }
    }
}
