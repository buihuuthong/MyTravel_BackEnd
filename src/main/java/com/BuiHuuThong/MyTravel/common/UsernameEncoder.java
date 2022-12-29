package com.BuiHuuThong.MyTravel.common;

public record UsernameEncoder(String email) {
	private static final String DELIMITER = "=";

	public String encode() {
		return DELIMITER + email;
	}

	public static UsernameEncoder decode(String username) {
		try {
			var pos = username.indexOf(DELIMITER);
			return new UsernameEncoder(
				username.substring(pos + DELIMITER.length())
			);
		} catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
			return null;
		}
	}
}
