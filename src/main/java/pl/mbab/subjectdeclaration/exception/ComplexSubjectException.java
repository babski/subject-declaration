package pl.mbab.subjectdeclaration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ComplexSubjectException extends RuntimeException {

    public ComplexSubjectException(String message) {
        super(message);
    }
}
