package com.BuiHuuThong.MyTravel.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// Các lỗi hệ thống
	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi không xác định"),
	SPRING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy"),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "Không có quyền truy cập"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Chưa được xác thực"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "Yêu cầu không hợp lệ"),

	// Các lỗi nghiệp vụ
	ACCOUNT_SUSPENDED(HttpStatus.FORBIDDEN, "Tài khoản không hoạt động"),
	WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "Tên đăng nhập và mật khẩu không khớp"),
	DELETE_CURRENT_ACCOUNT(HttpStatus.CONFLICT, "Không thể xóa tài khoản hiện tại"),
	USERNAME_HAS_BEEN_TAKEN(HttpStatus.CONFLICT, "Tên đăng nhập đã được sử dụng"),
	LICENSE_PLATES_ALREADY_EXISTS(HttpStatus.CONFLICT, "Biển kiểm soát đã tồn tại"),
	;
	private final HttpStatus status;
	private final String defaultMessage;
}
