package com.greenlight.global.application.processor;

import com.greenlight.global.domain.model.entity.Member;
import com.greenlight.global.domain.pwd.PasswordHelper;
import com.greenlight.global.infrastructure.exception.MemberAlreadySignUpMemberIdException;
import com.greenlight.global.infrastructure.persistence.MemberAdapterRepository;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class MemberSignUpProcessor {

	private final MemberAdapterRepository memberAdapterRepository;
	private final PasswordHelper passwordHelper;

	public MemberSignUpProcessor(MemberAdapterRepository memberAdapterRepository, PasswordHelper passwordHelper) {
		this.memberAdapterRepository = memberAdapterRepository;
		this.passwordHelper = passwordHelper;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Result execute(Command command) {
		Member member = memberAdapterRepository.findByMemberId(command.getMemberId());

		if (null != member) {
			throw new MemberAlreadySignUpMemberIdException();
		}

		memberAdapterRepository.save(getConvertingToMember(command));

		return new Result(command.getMemberId());
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
				.password(passwordHelper.encode(command.getPassword()))
				.nickname(command.getNickname())
				.phone(command.getPhone())
				.roles(command.getRole()).build();
	}

	@Getter
	public static class Result {
		private String memberId;

		public Result(String memberId) {
			this.memberId = memberId;
		}
	}

}
