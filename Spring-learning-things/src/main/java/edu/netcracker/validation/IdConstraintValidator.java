package edu.netcracker.validation;

import edu.netcracker.model.Entity;
import edu.netcracker.validation.annotation.IdValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validator;
import java.util.Objects;

/**
 * @author svku0919
 * @version 25.04.2022-15:17
 */

public class IdConstraintValidator implements ConstraintValidator<IdValid, Entity> {
    private final Validator v;
    private boolean idRequired;

    public IdConstraintValidator(Validator v) {
        this.v = v;
    }

    @Override
    public boolean isValid(Entity value, ConstraintValidatorContext context) {
        boolean isValid = idRequired ? Objects.nonNull(value.getId()) : Objects.isNull(value.getId());
        return isValid;
//        if (isValid) {
//            Set<ConstraintViolation<Entity>> violations = v.validate(value);
//            if (violations.isEmpty()) {
//                return true;
//            } else {
//                throw new ConstraintViolationException(violations);
//            }
//        } else return false;
    }

    @Override
    public void initialize(IdValid constraintAnnotation) {
        idRequired = constraintAnnotation.idRequired();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
