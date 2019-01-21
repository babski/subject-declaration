package pl.mbab.subjectdeclaration.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Hasło powinno zawierać od 6 do 20 znaków w tym przynajmniej jedną wielką i " +
            "małą literę, cyfrę oraz znak specjalny (!@#$%^&)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}