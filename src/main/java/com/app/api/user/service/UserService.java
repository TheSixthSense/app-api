package com.app.api.user.service;

import com.app.api.core.exception.BizException;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.entity.User;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // 기존 회원가입 여부 확인
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw BizException
                            .withUserMessageKey("exception.user.already.exist")
                            .build();
                });

        // save user
        userRepository.save(User.builder()
                .email(email)
                .userRoleType(userRegDTO.getUserRoleType())
                .build());
    }

    /** 어드민 권한 생성불가(관리자에게 문의) */
    private void checkUserRoleType(UserRegDTO userRegDTO) {
        if (userRegDTO.getUserRoleType() == null) {
            throw BizException
                    .withUserMessageKey("exception.user.role.type.null")
                    .build();
        }

        if (userRegDTO.getUserRoleType() == UserRoleType.ADMIN) {
            throw BizException
                    .withUserMessageKey("exception.user.create.admin.role")
                    .build();
        }
    }

    /** Apple Secret Token 에서 email 정보 추출 */
    @SuppressWarnings("unchecked")
    private String getEmailFromAppleSecretToken(String secretToken) {
        String[] chunks = secretToken.split("\\.");

        // payload base64 decode
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        // payload to json
        Gson gson = new Gson();
        Map<String, Object> payloadMap = gson.fromJson(payload, Map.class);

        return payloadMap.get("email").toString();
    }

    /** 이메일 형식 체크 */
    private void checkEmailForm(String email) {
        Matcher m = EMAIL.matcher(email);
        if(!m.matches()) {
            throw BizException
                    .withUserMessageKey("exception.common.email.form")
                    .build();
        }
    }
}
