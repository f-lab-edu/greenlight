package com.greenlight.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.greenlight.auth.configuration.jwt.JwtTokenProvider;
import com.greenlight.auth.domain.entity.Member;
import com.greenlight.auth.domain.repository.MemberRepository;
import com.greenlight.auth.ui.dto.request.LoginRequest;
import com.greenlight.auth.ui.dto.response.TokenResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;

    public TokenResponse generateTokenDTO(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        // 회원 인증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //토큰 발급
        Optional<TokenResponse> tokenResponse = jwtTokenProvider.generateTokenDTO(authentication)
                .map(tokenDTO -> {
                    memberRepository.saveRefreshToken(loginRequest.getMemberId(), tokenDTO);
                    return new TokenResponse(tokenDTO.getAccessToken(), tokenDTO.getRefreshToken(), tokenDTO.getTokenType(), tokenDTO.getExpiresIn());
                });
        log.info("generateTokenDTO tokenResponse = {}", tokenResponse);

        return tokenResponse.get();
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        log.info("loadUserByUsername #### {}", memberId);
        return memberRepository.findByMemberId(memberId)
                .map(member -> createUserDetails(member))
                .orElseThrow(() -> new UsernameNotFoundException(memberId + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
        log.info("createUserDetails #### {}", member);
        List<GrantedAuthority> grantedAuthorities = member.getMemberRoles().stream()
                .map(memberRole -> new SimpleGrantedAuthority(memberRole.name()))
                .collect(Collectors.toList());
        return new User(
                member.getMemberId()
                ,member.getPassword()
                ,grantedAuthorities
        );
    }
}
