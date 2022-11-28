package com.greenlight.auth.ui.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenlight.auth.domain.ErrorCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorResponse {

	@JsonIgnore
	private ErrorCode errorCode;

	@JsonProperty("result_code")
	private String resultCode;
	@JsonProperty("result_msg")
	private String resultMsg;

	public ErrorResponse(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.resultCode = errorCode.getCode();
		this.resultMsg = errorCode.getMessage();
	}
}
