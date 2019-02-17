package pl.mbab.subjectdeclaration.user.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
class UserRegistrationValidator {
    private final List<UserRegistrationValidationRule> rules;

    UserRegistrationValidateContext validateRegistration(UserDto userDto) {
        List<UserRegisrationInvalidConstraint> invalidConstraints = new ArrayList<>();
        rules.forEach(rule -> rule.validate(userDto).ifPresent(invalidConstraints::add));
        return new UserRegistrationValidateContext(invalidConstraints);
    }
}
