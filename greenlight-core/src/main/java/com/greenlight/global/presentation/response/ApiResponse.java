package com.greenlight.global.presentation.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
public class ApiResponse<T> {

	private final boolean success;
	private final T data;
	private final Error error;

	public ApiResponse(boolean success, T data, Error error) {
		this.success = success;
		this.data = data;
		this.error = error;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, null);
	}

	public static <T> ApiResponse<T> fail(Error error) {
		return new ApiResponse<>(false, null, error);
	}

	@Slf4j
	@Getter
	public static class Error {
		private String code;
		private String message;

		@JsonInclude(JsonInclude.Include.NON_NULL)
		@JsonProperty("validationErrors")
		private List<Error.ValidationError> errors;

		public Error(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public Error(String code, String message, List<ValidationError> errors) {
			this.code = code;
			this.message = message;
			this.errors = errors;
		}

		@Getter
		@Builder
		@RequiredArgsConstructor
		public static class ValidationError {
			private final String field;
			private final String message;

			public static Error.ValidationError of(final FieldError fieldError) {
				return Error.ValidationError.builder()
						.field(fieldError.getField())
						.message(fieldError.getDefaultMessage())
						.build();
			}
		}
	}

}
