package com.greenlight.global.presentation.request;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignUpRequestTest {

	private final boolean isPrint = true;
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	@DisplayName("Blank 체크")
	void beanValidationBlank() {
		//given	memberId 공백 password 널
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.memberId("")
			.password(null)
			.build();

		//when
		Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
		if (isPrint) printValidation(violations);

		//then
		Assertions.assertAll(
			() -> assertThat(violations.size()).isEqualTo(3)
		);
	}

	@Test
	@DisplayName("Size 체크")
	void beanValidationSize() {
		//given
		SignUpRequest signUpRequestMin = SignUpRequest.builder()
			.memberId("12")
			.password("12A")
			.nickname("1")
			.phone("010")
			.build();

		SignUpRequest signUpRequestMax = SignUpRequest.builder()
			.memberId("123456789012345678901234567890123456789012345678901234567890")
			.password("123456789012345678901234567890A")
			.nickname("12345678901")
			.phone("010123412345")
			.build();

		//when
		Set<ConstraintViolation<SignUpRequest>> violationsMin = validator.validate(signUpRequestMin);
		if (isPrint) printValidation(violationsMin);

		System.out.println();

		Set<ConstraintViolation<SignUpRequest>> violationsMax = validator.validate(signUpRequestMax);
		if (isPrint) printValidation(violationsMax);

		//then
		Assertions.assertAll(
			() -> assertThat(violationsMin.size()).isEqualTo(6),
			() -> assertThat(violationsMax.size()).isEqualTo(6)
		);
	}

	@Test
	@DisplayName("Pattern Password 체크")
	void beanValidationPatternPassword() {
		//given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.memberId("memberId")
			.password("12345678")
			.build();

		//when
		//숫자만
		Set<ConstraintViolation<SignUpRequest>> violationsInt = validator.validate(signUpRequest);
		if (isPrint) printValidation(violationsInt);

		//숫자 + 영문 소문자
		signUpRequest.setPassword("12345678a");
		Set<ConstraintViolation<SignUpRequest>> violationsIntAndLowerCase = validator.validate(signUpRequest);
		if (isPrint) printValidation(violationsIntAndLowerCase);

		//숫자 + 영문 소문자 + 특수문자 (정상)
		signUpRequest.setPassword("12345678a!");
		Set<ConstraintViolation<SignUpRequest>> violationsIntAndLowerCaseAndSpecialChar = validator.validate(signUpRequest);
		if (isPrint) printValidation(violationsIntAndLowerCaseAndSpecialChar);

		//then
		Assertions.assertAll(
			() -> assertThat(violationsInt.size()).isEqualTo(1),
			() -> assertThat(violationsIntAndLowerCase.size()).isEqualTo(1),
			() -> assertThat(violationsIntAndLowerCaseAndSpecialChar.size()).isEqualTo(0)
		);
	}

	@Test
	@DisplayName("Pattern NickName 체크")
	void beanValidationPatternNickName() {
		//given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.memberId("memberId")
			.password("12345678a!")
			.nickname("12!#")
			.build();

		//when
		//특수문자 사용 valid대상
		Set<ConstraintViolation<SignUpRequest>> violationsSpecialChar = validator.validate(signUpRequest);
		if (isPrint) printValidation(violationsSpecialChar);

		//특수문자 사용안함 정상
		signUpRequest.setNickname("닉네임");
		Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
		if (isPrint) printValidation(violations);

		//then
		Assertions.assertAll(
			() -> assertThat(violationsSpecialChar.size()).isEqualTo(1),
			() -> assertThat(violations.size()).isEqualTo(0)
		);
	}

	@Test
	@DisplayName("Pattern Phone 체크")
	void beanValidationPatternPhone() {
		//given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.memberId("memberId")
			.password("12345678a!")
			.nickname("닉네임")
			.phone("01011112222")
			.build();

		//when
		//휴대폰 형식에 맞지 않을때
		Set<ConstraintViolation<SignUpRequest>> violationsPatternType = validator.validate(signUpRequest);
		if (isPrint) printValidation(violationsPatternType);

		//휴대폰 형식에 맞을때 정상
		signUpRequest.setPhone("01029771245");
		Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
		if (isPrint) printValidation(violations);

		//then
		Assertions.assertAll(
			() -> assertThat(violationsPatternType.size()).isEqualTo(1),
			() -> assertThat(violations.size()).isEqualTo(0)
		);
	}

	@Test
	@DisplayName("Validation 정상 체크")
	void beanValidation() {
		//given
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.memberId("memberId")
			.password("12345678aaA!")
			.build();

		//when
		Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
		if (isPrint) printValidation(violations);

		//then
		Assertions.assertAll(
			() -> assertThat(violations.size()).isEqualTo(0)
		);
	}

	/**
	 * violations.stream().collect(Collectors.toList())
	 */
	private void printValidation(Set<ConstraintViolation<SignUpRequest>> violations) {
		for (ConstraintViolation<SignUpRequest> violation : violations) {
			System.out.println("violation = " + violation);
			System.out.println("propertyName = " + violation.getPropertyPath());
			System.out.println("InvalidValue = " + violation.getInvalidValue());
			System.out.println("Message = " + violation.getMessage());
		}
	}

}
