package com.greenlight.global.presentation.request;

import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.model.code.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpRequest {

	private String email;
	private String password;
	private String nickname;
	private String phone;

	public MemberSignUpProcessor.Command toCommand() {
		return new MemberSignUpProcessor.Command(email, password, nickname, phone, MemberRole.ROLE_USER.name());
	}

}
