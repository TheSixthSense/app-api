package com.app.api.user.aop;

import com.app.api.core.exception.BizException;
import com.app.api.user.dto.UserDTO;
import com.app.api.user.entity.User;
import com.app.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class UserAspect {

    private final UserRepository userRepository;

    @Around("execution(* *(.., @User (*), ..))")
    public Object coverAroundUserAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // User 정보 반환
        Object[] args = Arrays.stream(joinPoint.getArgs()).map(data -> {
            if (data instanceof UserDTO) {
                if (principal == "anonymousUser") return null; // 비로그인시 null 반환
                assert principal instanceof UserDetails;
                UserDetails userDetails = (UserDetails)principal;
                Long userId = Long.parseLong(userDetails.getUsername()); // user id
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> BizException
                                .withUserMessageKey("exception.user.not.found")
                                .build());
                data =  UserDTO.from(user);
            }
            return data;
        }).toArray();

        return joinPoint.proceed(args);
    }
}
