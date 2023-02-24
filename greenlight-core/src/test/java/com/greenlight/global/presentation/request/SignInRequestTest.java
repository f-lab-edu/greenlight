package com.greenlight.global.presentation.request;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignInRequestTest {

	private final boolean isPrint = true;
	private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	@DisplayName("Blank 체크")
	void beanValidationBlank() {
		//given	memberId 공백 password 널
		SignInRequest signInRequest = SignInRequest.builder()
			.memberId("")
			.password(null)
			.build();

		//when
		Set<ConstraintViolation<SignInRequest>> violations = validator.validate(signInRequest);
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
		SignInRequest signInRequestMin = SignInRequest.builder()
			.memberId("12")
			.password("12A")
			.build();

		SignInRequest signInRequestMax = SignInRequest.builder()
			.memberId("123456789012345678901234567890123456789012345678901234567890")
			.password("123456789012345678901234567890A")
			.build();

		//when
		Set<ConstraintViolation<SignInRequest>> violationsMin = validator.validate(signInRequestMin);
		if (isPrint) printValidation(violationsMin);

		System.out.println();

		Set<ConstraintViolation<SignInRequest>> violationsMax = validator.validate(signInRequestMax);
		if (isPrint) printValidation(violationsMax);

		//then
		Assertions.assertAll(
			() -> assertThat(violationsMin.size()).isEqualTo(3),
			() -> assertThat(violationsMax.size()).isEqualTo(3)
		);
	}

	@Test
	@DisplayName("Pattern 체크")
	void beanValidationPattern() {
		//given
		SignInRequest signInRequest = SignInRequest.builder()
			.memberId("memberId")
			.password("12345678")
			.build();

		//when
		//숫자만
		Set<ConstraintViolation<SignInRequest>> violationsInt = validator.validate(signInRequest);
		if (isPrint) printValidation(violationsInt);

		//숫자 + 영문 소문자
		signInRequest.setPassword("12345678a");
		Set<ConstraintViolation<SignInRequest>> violationsIntAndLowerCase = validator.validate(signInRequest);
		if (isPrint) printValidation(violationsIntAndLowerCase);

		//숫자 + 영문 소문자 + 특수문자 (정상)
		signInRequest.setPassword("12345678a!");
		Set<ConstraintViolation<SignInRequest>> violationsIntAndLowerCaseAndSpecialChar = validator.validate(signInRequest);
		if (isPrint) printValidation(violationsIntAndLowerCaseAndSpecialChar);

		//then
		Assertions.assertAll(
			() -> assertThat(violationsInt.size()).isEqualTo(1),
			() -> assertThat(violationsIntAndLowerCase.size()).isEqualTo(1),
			() -> assertThat(violationsIntAndLowerCaseAndSpecialChar.size()).isEqualTo(0)
		);
	}

	@Test
	@DisplayName("Validation 정상 체크")
	void beanValidation() {
		//given
		SignInRequest signInRequest = SignInRequest.builder()
			.memberId("memberId")
			.password("12345678aaA!")
			.build();

		//when
		Set<ConstraintViolation<SignInRequest>> violations = validator.validate(signInRequest);
		if (isPrint) printValidation(violations);

		//then
		Assertions.assertAll(
			() -> assertThat(violations.size()).isEqualTo(0)
		);
	}

	/**
	 * violations.stream().collect(Collectors.toList())
	 */
	private void printValidation(Set<ConstraintViolation<SignInRequest>> violations) {
		for (ConstraintViolation<SignInRequest> violation : violations) {
			System.out.println("violation = " + violation);
			System.out.println("propertyName = " + violation.getPropertyPath());
			System.out.println("InvalidValue = " + violation.getInvalidValue());
			System.out.println("Message = " + violation.getMessage());
		}
	}

}
