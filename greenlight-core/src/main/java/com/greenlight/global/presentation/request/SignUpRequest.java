package com.greenlight.global.presentation.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.model.constants.member.MemberRole;

@Getter
@Setter
@ToString
public class SignUpRequest {

	@NotBlank(message = "멤버ID는 필수값입니다.")
	@Size(min = 3, max = 50)
	private String memberId;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "패스워드는 필수값입니다.")
	@Size(min = 3, max = 100)
	private String password;

	@Size(max = 50)
	private String nickname;

	@NotNull
	@Size(min = 10, max = 11)
	@Pattern(regexp = "010[2-9][0-9]{3}[0-9]{4}|01[16789][2-9][0-9]{2,3}[0-9]{4}", message = "형식이 잘못되었습니다")
	private String phone;

	public MemberSignUpProcessor.Command toCommand() {
		return new MemberSignUpProcessor.Command(memberId, password, nickname, phone, MemberRole.ROLE_USER.name());
	}

}
