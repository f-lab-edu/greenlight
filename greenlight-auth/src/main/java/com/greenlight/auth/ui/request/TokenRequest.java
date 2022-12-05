package com.greenlight.auth.ui.request;

import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

	@NotEmpty(message = "{validation.not.empty.accessToken}")
	@JsonProperty("access_token")
	private String accessToken;

	@NotEmpty(message = "test")
	@JsonProperty("refresh_token")
	private String refreshToken;

}
