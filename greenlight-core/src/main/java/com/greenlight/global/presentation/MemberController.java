package com.greenlight.global.presentation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.greenlight.global.application.facade.MemberManager;
import com.greenlight.global.application.processor.MemberSignInProcessor;
import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.model.constants.common.GreenLightConstants;
import com.greenlight.global.presentation.request.SignInRequest;
import com.greenlight.global.presentation.request.SignUpRequest;
import com.greenlight.global.presentation.response.ApiResponse;
import com.greenlight.global.presentation.response.SignInResponse;
import com.greenlight.global.presentation.response.SignUpResponse;

@RestController
@RequestMapping(value = GreenLightConstants.PATH_API_VERSION_1, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

	private final MemberManager memberManager;

	public MemberController(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/member/signUp")
	public ApiResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		MemberSignUpProcessor.Result result = memberManager.signUp(signUpRequest.toCommand());
		return ApiResponse.success(SignUpResponse.from(result));
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/member/signIn")
	public ApiResponse<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
		MemberSignInProcessor.Result result = memberManager.signIn(signInRequest.toCommand());
		return ApiResponse.success(SignInResponse.from(result));
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/member/logout")
	public String logout() {
		return "ok";
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/user")
	public String user(@RequestBody SignInRequest signInRequest) {
		return "user";
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/manager")
	public String manager(@RequestBody SignInRequest signInRequest) {
		return "manager";
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/admin")
	public String admin(@RequestBody SignInRequest signInRequest) {
		return "admin";
	}
}
