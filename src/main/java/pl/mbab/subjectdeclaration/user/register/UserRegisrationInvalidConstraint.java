package pl.mbab.subjectdeclaration.user.register;

import lombok.Value;

@Value
class UserRegisrationInvalidConstraint {
    String fieldName;
    String  errorCode;
}
