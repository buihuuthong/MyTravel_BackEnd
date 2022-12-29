package com.BuiHuuThong.MyTravel.exception;

import com.BuiHuuThong.MyTravel.util.Util;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedCheckedException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.util.NestedServletException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
		if (Objects.equals(ex.getIsLogging(), true)) {
			log.warn("Hmm!", ex);
		}
		var response = new ErrorResponse(ex.getCode(), ex.getMessage());
		return buildJsonResponseEntity(response);
	}

	@ExceptionHandler({ BindException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
		final var defaultMessage = "Dữ liệu không hợp lệ";
		var message = defaultMessage;
		if (ex instanceof BindException castedEx) {
			message = Util.getBindExceptionMessage(castedEx, defaultMessage);
		} else if (ex instanceof ConstraintViolationException exception) {
			message = Util.getConstraintViolationExceptionMessage(exception, defaultMessage);
		}
		var response = new ErrorResponse(ErrorCode.BAD_REQUEST, message);
		return buildJsonResponseEntity(response);
	}

	@ExceptionHandler({
		MaxUploadSizeExceededException.class,
		MissingServletRequestPartException.class
	})
	public ResponseEntity<ErrorResponse> handleCommonBadRequestException(Exception ex) {
		var message = ErrorCode.BAD_REQUEST.getDefaultMessage();
		if (ex instanceof MaxUploadSizeExceededException) {
			message = "Kích thước file upload quá lớn";
		} else if (ex instanceof MissingServletRequestPartException castedEx) {
			message =	"Thiếu tham số " + castedEx.getRequestPartName();
		}
		var response = new ErrorResponse(ErrorCode.BAD_REQUEST, message);
		return buildJsonResponseEntity(response);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException() {
		var response = new ErrorResponse(ErrorCode.ACCESS_DENIED);
		return buildJsonResponseEntity(response);
	}

	@ExceptionHandler({
		NestedCheckedException.class,
		NestedRuntimeException.class,
		NestedServletException.class,
		ServletException.class
	})
	public ResponseEntity<ErrorResponse> handleSpringException(Exception ex) {
		log.error("Hmm!", ex);
		var response = new ErrorResponse(ErrorCode.SPRING_ERROR);
		return buildJsonResponseEntity(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnwantedException(Exception ex) {
		log.error("Ops!", ex);
		var response = new ErrorResponse(ErrorCode.SERVER_ERROR);
		return buildJsonResponseEntity(response);
	}

	private ResponseEntity<ErrorResponse> buildJsonResponseEntity(ErrorResponse response) {
		return ResponseEntity
			.status(response.code().getStatus())
			.contentType(MediaType.APPLICATION_JSON)
			.body(response);
	}
}
