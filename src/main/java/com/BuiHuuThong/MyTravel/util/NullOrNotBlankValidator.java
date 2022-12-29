package com.BuiHuuThong.MyTravel.util;

import org.springframework.lang.Nullable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {
	public boolean isValid(
		@Nullable String value,
		ConstraintValidatorContext constraintValidatorContext
	) {
		return value == null || !value.trim().isEmpty();
	}
}
