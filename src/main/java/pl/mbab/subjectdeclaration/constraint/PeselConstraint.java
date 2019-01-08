package pl.mbab.subjectdeclaration.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PeselValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PeselConstraint {
    String message() default "Invalid pesel number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}