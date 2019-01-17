package pl.mbab.subjectdeclaration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ComplexSubjectException extends RuntimeException {

    public ComplexSubjectException() {
        super();
    }

    public ComplexSubjectException(String message) {
        super(message);
    }

    public ComplexSubjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
