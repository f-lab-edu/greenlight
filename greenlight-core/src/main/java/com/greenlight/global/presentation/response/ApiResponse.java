package com.greenlight.global.presentation.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private boolean success;
	private T data;
	private Error error;

	public ApiResponse(boolean success, T data, Error error) {
		this.success = success;
		this.data = data;
		this.error = error;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, null);
	}

	public static ApiResponse fail(Error error) {
		return new ApiResponse<>(false, null, error);
	}

	@Getter
	public static class Error {
		private String code;
		private String message;

		public Error(String code, String message) {
			this.code = code;
			this.message = message;
		}
	}

}
