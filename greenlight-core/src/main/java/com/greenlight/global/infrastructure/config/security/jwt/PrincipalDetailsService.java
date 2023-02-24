package com.greenlight.global.infrastructure.config.security.jwt;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.greenlight.global.domain.model.Member;
import com.greenlight.global.domain.model.constants.common.CommonBoolean;
import com.greenlight.global.domain.repository.member.MemberRepository;
import com.greenlight.global.infrastructure.exception.MemberIdNotFoundException;

@RequiredArgsConstructor
@Component
public class PrincipalDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Member member = memberRepository.findByMemberId(memberId);
		if (null == member) {
			throw new MemberIdNotFoundException();
		}
		return createUser(member);
	}

	private User createUser(Member member) {
		if (CommonBoolean.N.getIntYN() == member.getActivated()) {
			throw new MemberIdNotFoundException();
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		member.getRoleList()
		.forEach(r -> {
			grantedAuthorities.add(() -> r);
		});

		return new User(member.getMemberId(), member.getPassword(), grantedAuthorities);
	}

}
