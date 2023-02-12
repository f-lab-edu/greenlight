package com.greenlight.auth.ui.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenlight.auth.domain.ErrorCode;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorResponse {

	@JsonIgnore
	private ErrorCode errorCode;

	private String code;
	private String msg;

	public ErrorResponse(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.code = errorCode.getCode();
		this.msg = errorCode.getMessage();
	}
}
