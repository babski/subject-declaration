package pl.mbab.subjectdeclaration.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeselValidator implements
        ConstraintValidator<PeselConstraint, String> {

    @Override
    public void initialize(PeselConstraint peselNumber) {
    }

    @Override
    public boolean isValid(String pesel,
                           ConstraintValidatorContext cxt) {
        if (pesel.isEmpty() || pesel.length() != 11) return false;

        String[] p = pesel.split("");

        int sum = 9 * Integer.parseInt(p[0])
                + 7 * Integer.parseInt(p[1])
                + 3 * Integer.parseInt(p[2])
                + 1 * Integer.parseInt(p[3])
                + 9 * Integer.parseInt(p[4])
                + 7 * Integer.parseInt(p[5])
                + 3 * Integer.parseInt(p[6])
                + 1 * Integer.parseInt(p[7])
                + 9 * Integer.parseInt(p[8])
                + 7 * Integer.parseInt(p[9]);

        return (Integer.parseInt(p[10]) == sum % 10) ? true : false;
    }

}
