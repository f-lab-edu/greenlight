package com.greenlight.global.application.processor;

import com.greenlight.global.domain.exception.MemberAlreadySignUpEmailException;
import com.greenlight.global.domain.model.Member;
import com.greenlight.global.domain.repository.MemberRepository;
import lombok.Getter;

public class MemberSignUpProcessor {

	private final MemberRepository memberRepository;

	public MemberSignUpProcessor(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Result execute(Command command) {
		Member member = memberRepository.findByEmail(command.getEmail());

		if (null != member) {
			throw new MemberAlreadySignUpEmailException();
		}

		Long memberId = memberRepository.save(command);

		return new Result(memberId);
	}

	@Getter
	public static class Command {
		private String email;
		private String password;
		private String nickname;
		private String phone;
		private String role;

		public Command(String email, String password, String nickname, String phone, String role) {
			this.email = email;
			this.password = password;
			this.nickname = nickname;
			this.phone = phone;
			this.role = role;
		}
	}

	@Getter
	public class Result {
		private Long memberId;

		public Result(Long memberId) {
			this.memberId = memberId;
		}
	}

}
