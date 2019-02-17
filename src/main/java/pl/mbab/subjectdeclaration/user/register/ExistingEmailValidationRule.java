package pl.mbab.subjectdeclaration.user.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mbab.subjectdeclaration.service.UserService;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Component
class ExistingEmailValidationRule implements UserRegistrationValidationRule {

    private final UserService userService;

    @Override
    public Optional<UserRegisrationInvalidConstraint> validate(UserDto user) {
        return ofNullable(userService.findByEmail(user.getEmail()))
                .map(existingUser -> new UserRegisrationInvalidConstraint("email", "Istnieje już konto zarejestrowane na ten adres email"));
    }
}
