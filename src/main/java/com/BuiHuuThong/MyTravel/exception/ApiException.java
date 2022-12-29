package com.BuiHuuThong.MyTravel.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
	private final ErrorCode code;
	private Boolean isLogging = false;

	public ApiException(ErrorCode code) {
		this(code, code.getDefaultMessage(), null);
	}

	public ApiException(ErrorCode code, String message) {
		this(code, message, null);
	}

	public ApiException(ErrorCode code, Throwable cause) {
		this(code, code.getDefaultMessage(), cause);
	}

	public ApiException(ErrorCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ApiException setIsLogging(boolean isLogging) {
		this.isLogging = isLogging;
		return this;
	}
}
