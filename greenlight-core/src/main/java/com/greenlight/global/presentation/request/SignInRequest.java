package com.greenlight.global.presentation.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.greenlight.global.application.processor.MemberSignInProcessor;
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
public class SignInRequest {

	@NotBlank(message = "멤버ID는 필수값입니다.")
    @Size(min = 3, max = 50, message = "멤버ID는 3~50 사이여야 합니다.")
    private String memberId;

	@NotBlank(message = "패스워드는 필수값입니다.")
    @Size(min = 8, max = 20)
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    public MemberSignInProcessor.Command toCommand() {
        return new MemberSignInProcessor.Command(memberId, password);
    }

}
