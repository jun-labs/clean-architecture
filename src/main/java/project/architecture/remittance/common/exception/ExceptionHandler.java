package project.architecture.remittance.common.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.architecture.remittance.common.exception.common.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ErrorResponse catchBusinessException(BusinessException businessException) {
        return ErrorResponse.of(businessException);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse catchBadRequest(BusinessException businessException) {
        return ErrorResponse.of(businessException);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ErrorResponse catchUnresolvedException(BusinessException businessException) {
        return ErrorResponse.of(businessException);
    }
}
