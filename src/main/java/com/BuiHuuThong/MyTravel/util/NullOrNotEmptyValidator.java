package com.BuiHuuThong.MyTravel.util;

import org.springframework.lang.Nullable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotEmptyValidator implements ConstraintValidator<NullOrNotEmpty, String> {
	public boolean isValid(
		@Nullable String value,
		ConstraintValidatorContext constraintValidatorContext
	) {
		return value == null || !value.isEmpty();
	}
}
