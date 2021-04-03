package ua.edu.ukma.krukovska.bookservice.persistence.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsbnValidator.class)
public @interface IsbnConstraint {
    String message() default "Wrong ISBN format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
