package com.greenlight.auth.application;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
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

import com.greenlight.auth.configuration.jwt.JwtProperties;
import com.greenlight.auth.configuration.jwt.JwtTokenProvider;
import com.greenlight.auth.domain.ErrorCode;
import com.greenlight.auth.domain.entity.Member;
import com.greenlight.auth.domain.repository.MemberRepository;
import com.greenlight.auth.exception.JwtException;
import com.greenlight.auth.ui.dto.request.LoginRequest;
import com.greenlight.auth.ui.dto.request.TokenRequest;
import com.greenlight.auth.ui.dto.response.TokenResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final JwtProperties jwtProperties;

    public TokenResponse generateTokenDTO(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        // 회원 인증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 토큰 발급
        Optional<TokenResponse> tokenResponse = jwtTokenProvider.generateTokenDTO(authentication)
                .map(tokenDTO -> {
                    memberRepository.saveRefreshToken(loginRequest.getMemberId(), tokenDTO);
                    return new TokenResponse(tokenDTO.getAccessToken(), tokenDTO.getRefreshToken());
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
                , member.getPassword()
                , grantedAuthorities
        );
    }

    public TokenResponse refreshTokenDTO(TokenRequest tokenRequest) {
    	// 토큰을 갱신해주자
		String accessToken = resolveToken(tokenRequest.getAccessToken());
		String refreshToken = tokenRequest.getRefreshToken();

		// 흠 getExpiration을 하면 만료되버리면 에러 던지네
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		log.info("accessToken : " + accessToken);
//		log.info("refreshToken : " + refreshToken);
//		long dateAc = jwtTokenProvider.parseClaims(accessToken).getExpiration().getTime();
//		log.info("now : " + simpleDateFormat.format(new Date()));

		ErrorCode errorCode = ErrorCode.SUCCESS;

		try {
			jwtTokenProvider.validateToken(accessToken);
		} catch (JwtException eje) {
			log.info("access-token : {}" + eje.getErrorCode().getCode());
			errorCode = eje.getErrorCode();
		}

		try {
			jwtTokenProvider.validateToken(refreshToken);
		} catch (JwtException eje) {
			log.info("refresh-token : {}" + eje.getErrorCode().getCode());
			errorCode = eje.getErrorCode();
		}

		return new TokenResponse();
	}

	private String resolveToken(String token) {
    	if(token.startsWith(jwtProperties.getTokenHeaderPrefix())) {
			return token.substring(7);
		}
    	return token;
	}
}
