package edu.netcracker.validation.annotation;

import edu.netcracker.validation.IdConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author svku0919
 * @version 25.04.2022-15:17
 */

@Constraint(validatedBy = IdConstraintValidator.class)
@Target({ElementType.TYPE, ElementType.PARAMETER, METHOD })
@Retention(RUNTIME)
@Documented
public @interface IdConstraint {
    boolean idRequired() default false;

    String message() default "Id Constraint violated";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
