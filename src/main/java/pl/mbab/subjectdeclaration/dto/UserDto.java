package pl.mbab.subjectdeclaration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mbab.subjectdeclaration.constraint.FieldMatch;
import pl.mbab.subjectdeclaration.constraint.PasswordConstraint;
import pl.mbab.subjectdeclaration.constraint.PeselConstraint;
import pl.mbab.subjectdeclaration.model.user.Field;
import pl.mbab.subjectdeclaration.model.user.Gender;
import pl.mbab.subjectdeclaration.model.user.Semester;
import pl.mbab.subjectdeclaration.model.user.UserStatus;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Pola haseł powinny do siebie pasować"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "Pola email powinny do siebie pasować")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {

    private Long id;
    @Size(min = 3, max = 20, message = "Imię powinno zawierać od 3 do 20 znaków")
    private String firstName;

    @Size(min = 3, max = 40, message = "Nazwisko powinno zawierać od 3 do 40 znaków")
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

    @Size(min = 6, message = "Email powinien zawierać przynajmniej 6 znaków")
    private String email;

    private String confirmEmail;

    @AssertTrue(message = "Należy zaznaczyć powyższe pole")
    private Boolean terms;

    private UserStatus userStatus;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}