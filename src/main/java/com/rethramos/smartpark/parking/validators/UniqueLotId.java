package com.rethramos.smartpark.parking.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueLotIdValidator.class })
@Documented
public @interface UniqueLotId {
    String message() default "lotId must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
