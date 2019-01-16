package pl.mbab.subjectdeclaration.web.dto;

import lombok.Getter;
import lombok.Setter;
import pl.mbab.subjectdeclaration.constraint.FieldMatch;
import pl.mbab.subjectdeclaration.constraint.PasswordConstraint;
import pl.mbab.subjectdeclaration.constraint.PeselConstraint;
import pl.mbab.subjectdeclaration.model.student.Field;
import pl.mbab.subjectdeclaration.model.student.Gender;
import pl.mbab.subjectdeclaration.model.student.Semester;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
@Getter
@Setter
public class UserRegistrationDto {

    @Size(min=3, max=30, message = "Imię powinno zawierać od 3 do 20 znaków")
    private String firstName;

    @Size(min=3, max=30, message = "Nazwisko powinno zawierać od 3 do 20 znaków")
    private String lastName;

    @PeselConstraint
    private String pesel;

    @NotNull(message = "Proszę wybrać płeć biologiczną")
    private Gender gender;

    private Semester semester;

    private Field field;

    @PasswordConstraint
    private String password;

    private String confirmPassword;

    @Size(min=6, message = "Email powinien zawierać przynajmniej 6 znaków")
    private String email;

    private String confirmEmail;

    @AssertTrue
    private Boolean terms;

}