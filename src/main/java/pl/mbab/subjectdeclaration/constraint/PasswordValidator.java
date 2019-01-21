package pl.mbab.subjectdeclaration.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements
        ConstraintValidator<PasswordConstraint, String> {

    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&]).{6,20})";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    @Override
    public void initialize(PasswordConstraint password) {
    }

    @Override
    public boolean isValid(final String password,
                           ConstraintValidatorContext cxt) {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
