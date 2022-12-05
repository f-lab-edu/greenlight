package com.greenlight.auth.ui.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {

	private boolean success;
    private T data;
    private T error;

	public static <T> Response<T> success(T data) {
		return new Response<>(true, data, null);
	}

    public static <T> Response<T> error(T data) {
    	return new Response<>(false, null, data);
    }
}
