package com.greenlight.global.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenlight.global.domain.SecurityUtil;
import com.greenlight.global.domain.entity.Authority;
import com.greenlight.global.domain.entity.User;
import com.greenlight.global.domain.repository.UserRepository;
import com.greenlight.global.dto.UserDto;
import com.greenlight.global.exception.DuplicateUserException;
import com.greenlight.global.exception.NotFoundUserException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUserId(userDto.getUserId()).orElse(null) != null) {
            throw new DuplicateUserException();
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        log.info("userDto :: {}", userDto);

        User user = User.builder()
                .userId(userDto.getUserId())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String userId) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUserId(userId).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUserId)
                        .orElseThrow(() -> new NotFoundUserException())
        );
    }
}
