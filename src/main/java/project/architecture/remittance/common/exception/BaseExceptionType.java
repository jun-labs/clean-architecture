package project.architecture.remittance.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    int getErrorCode();

    String getMessage();

    HttpStatus getHttpStatus();

}

