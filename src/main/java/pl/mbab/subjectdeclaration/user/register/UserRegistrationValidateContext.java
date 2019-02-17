package pl.mbab.subjectdeclaration.user.register;

import org.springframework.validation.BindingResult;

import java.util.List;

import static java.util.Collections.unmodifiableList;

class UserRegistrationValidateContext {
    private final List<UserRegisrationInvalidConstraint> invalidConstraints;

    UserRegistrationValidateContext(List<UserRegisrationInvalidConstraint> invalidConstraints) {
        this.invalidConstraints = unmodifiableList(invalidConstraints);
    }

    public boolean hasErrors() {
        return !invalidConstraints.isEmpty();
    }

    public void populateErrors(BindingResult result) {
        invalidConstraints.forEach(constraint -> result.rejectValue(constraint.getFieldName(), null, constraint.getErrorCode()));
    }
}
