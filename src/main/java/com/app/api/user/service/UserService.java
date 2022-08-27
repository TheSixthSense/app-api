package com.app.api.user.service;

import com.app.api.auth.service.AuthService;
import com.app.api.core.exception.BizException;
import com.app.api.user.dto.UserDTO;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.dto.UserUpdateDTO;
import com.app.api.user.dto.UserViewDTO;
import com.app.api.user.entity.User;
import com.app.api.user.entity.UserWithdraw;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.repository.UserRepository;
import com.app.api.user.repository.UserWithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.app.api.common.util.form.Form.checkEmailForm;
import static com.app.api.common.util.jwt.JwtUtil.getEmailFromAppleSecretToken;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserWithdrawRepository userWithdrawRepository;
    private final ModelMapper modelMapper;

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
     * 회원탈퇴
     */
    @Transactional
    public void userWithDraw(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() ->
                        BizException.withUserMessage("exception.user.not.found").build());

        UserWithdraw userWithdraw = modelMapper.map(user, UserWithdraw.class);
        userWithdrawRepository.save(userWithdraw);
        userRepository.deleteById(userDTO.getId());
    }

    /**
     * 회원정보 조회
     */
    public UserViewDTO convert(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserViewDTO.class);
    }

    /**
     * 회원정보 수정
     */
    @Transactional
    public UserViewDTO updateUserInfo(UserDTO userDTO, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> BizException.withUserMessageKey("exception.user.not.found").build());

        user.updateUserInfo(userUpdateDTO);
        userRepository.save(user);
        return modelMapper.map(user, UserViewDTO.class);
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
}
