package pl.mbab.subjectdeclaration.web.dto;

import lombok.Getter;
import lombok.Setter;
import pl.mbab.subjectdeclaration.constraint.FieldMatch;
import pl.mbab.subjectdeclaration.constraint.PasswordConstraint;
import pl.mbab.subjectdeclaration.constraint.PeselConstraint;
import pl.mbab.subjectdeclaration.model.user.Field;
import pl.mbab.subjectdeclaration.model.user.Gender;
import pl.mbab.subjectdeclaration.model.user.Semester;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Pola haseł powinny do siebie pasować"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "Pola email powinny do siebie pasować")
})
@Getter
@Setter
public class UserRegistrationDto {

    @Size(min=3, max=20, message = "Imię powinno zawierać od 3 do 20 znaków")
    private String firstName;

    @Size(min=3, max=40, message = "Nazwisko powinno zawierać od 3 do 40 znaków")
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

    @AssertTrue(message = "Należy zaznaczyć powyższe pole")
    private Boolean terms;

}