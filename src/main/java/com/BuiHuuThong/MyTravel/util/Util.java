package com.BuiHuuThong.MyTravel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolationException;
import java.security.Security;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
	private static final Random random = new Random();
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String getBindExceptionMessage(BindException ex, String defaultMessage) {
		if (!ex.hasErrors() || ex.getAllErrors().isEmpty()) {
			return defaultMessage;
		}
		return ex.getAllErrors().get(0).getDefaultMessage();
	}

	public static String getConstraintViolationExceptionMessage(
		ConstraintViolationException ex,
		String defaultMessage
	) {
		var firstViolation = ex.getConstraintViolations().stream().findFirst();
		if (firstViolation.isEmpty()) {
			return defaultMessage;
		}
		return firstViolation.get().getMessage();
	}

	public static void allowTLSv1() {
		final var DISABLED_ALGORITHMS_PROPERTY = "jdk.tls.disabledAlgorithms";
		var disabledAlgorithms = Security.getProperty(DISABLED_ALGORITHMS_PROPERTY).split(",");
		var newDisabledAlgorithms = Stream.of(disabledAlgorithms)
			.map(String::trim)
			.filter(algorithm -> !algorithm.equals("TLSv1"))
			.collect(Collectors.joining(", "));
		Security.setProperty(DISABLED_ALGORITHMS_PROPERTY, newDisabledAlgorithms);
	}

	public static long tinhSoTrang(long count, long size) {
		return (long) Math.ceil((double) count / size);
	}

	public static String generateRandomString(int length) {
		char[] CHARS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z',
		};
		var builder = new StringBuilder();
		for (var i = 0; i < length; i++) {
			var index = random.nextInt(CHARS.length);
			builder.append(CHARS[index]);
		}
		return builder.toString();
	}

	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> T fromJson(String json, Class<T> type) {
		try {
			return objectMapper.readValue(json, type);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
