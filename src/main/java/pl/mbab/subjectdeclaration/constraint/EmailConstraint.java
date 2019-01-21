package pl.mbab.subjectdeclaration.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
    String message() default "Nieprawidłowy format adresu email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}