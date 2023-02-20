package com.greenlight.global.application.processor;

import com.greenlight.global.domain.model.Member;
import com.greenlight.global.domain.repository.member.MemberRepository;
import com.greenlight.global.infrastructure.exception.MemberAlreadySignUpMemberIdException;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class MemberSignUpProcessor {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberSignUpProcessor(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Result execute(Command command) {
		Member member = memberRepository.findByMemberId(command.getMemberId());

		if (null != member) {
			throw new MemberAlreadySignUpMemberIdException();
		}

		int memberId = memberRepository.save(getConvertingToMember(command));

		return new Result(memberId);
	}

	@Getter
	@ToString
	public static class Command {
		private String memberId;
		private String password;
		private String nickname;
		private String phone;
		private String role;

		public Command(String memberId, String password, String nickname, String phone, String role) {
			this.memberId = memberId;
			this.password = password;
			this.nickname = nickname;
			this.phone = phone;
			this.role = role;
		}
	}

	private Member getConvertingToMember(Command command) {
		return Member.builder().memberId(command.getMemberId())
				.password(passwordEncoder.encode(command.getPassword()))
				.nickname(command.getNickname())
				.phone(command.getPhone())
				.roles(command.getRole()).build();
	}

	@Getter
	public class Result {
		private int memberId;

		public Result(int memberId) {
			this.memberId = memberId;
		}
	}

}
