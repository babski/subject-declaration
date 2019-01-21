package pl.mbab.subjectdeclaration.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements
        ConstraintValidator<EmailConstraint, String> {

    @Override
    public void initialize(EmailConstraint email) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext cxt) {
        String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
