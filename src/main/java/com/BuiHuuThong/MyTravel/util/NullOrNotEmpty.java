package com.BuiHuuThong.MyTravel.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotEmptyValidator.class)
public @interface NullOrNotEmpty {
	String message();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
