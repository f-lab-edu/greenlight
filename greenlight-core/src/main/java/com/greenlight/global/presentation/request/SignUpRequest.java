package com.greenlight.global.presentation.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.model.constants.member.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

	@NotBlank(message = "멤버ID는 필수값입니다.")
	@Size(min = 3, max = 50, message = "멤버ID는 3~50 사이여야 합니다.")
	private String memberId;

	@NotBlank(message = "패스워드는 필수값입니다.")
	@Size(min = 8, max = 20)
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	private String password;

	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
	private String nickname;

	@Size(min = 10, max = 11)
	@Pattern(regexp = "010[2-9][0-9]{3}[0-9]{4}|01[16789][2-9][0-9]{2,3}[0-9]{4}", message = "휴대폰 형식이 잘못되었습니다")
	private String phone;

	public MemberSignUpProcessor.Command toCommand() {
		return new MemberSignUpProcessor.Command(memberId, password, nickname, phone, MemberRole.ROLE_USER.name());
	}

}
