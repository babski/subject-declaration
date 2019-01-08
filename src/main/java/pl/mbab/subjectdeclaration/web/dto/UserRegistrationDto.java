package pl.mbab.subjectdeclaration.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pl.mbab.subjectdeclaration.constraint.FieldMatch;
import pl.mbab.subjectdeclaration.constraint.PeselConstraint;
import pl.mbab.subjectdeclaration.model.student.Field;
import pl.mbab.subjectdeclaration.model.student.Gender;
import pl.mbab.subjectdeclaration.model.student.Semester;

import javax.validation.constraints.AssertTrue;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
@Getter
@Setter
public class UserRegistrationDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @PeselConstraint
    private String pesel;

    private Gender gender;

    private Semester semester;

    private Field field;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;

    @AssertTrue
    private Boolean terms;

}