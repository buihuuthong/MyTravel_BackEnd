package com.BuiHuuThong.MyTravel.exception;

public record ErrorResponse(ErrorCode code, String message) {
	public ErrorResponse(ErrorCode code) {
		this(code, code.getDefaultMessage());
	}
}
