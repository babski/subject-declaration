package pl.mbab.subjectdeclaration.user.register;

import java.util.Optional;

interface UserRegistrationValidationRule {
    Optional<UserRegisrationInvalidConstraint> validate(UserDto user);
}
