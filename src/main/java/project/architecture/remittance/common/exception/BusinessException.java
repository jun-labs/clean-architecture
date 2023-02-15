package project.architecture.remittance.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final BaseExceptionType baseExceptionType;

    private BusinessException(BaseExceptionType baseExceptionType) {
        super(baseExceptionType.getMessage());
        this.baseExceptionType = baseExceptionType;
    }

    public static BusinessException of(BaseExceptionType baseExceptionType) {
        return new BusinessException(baseExceptionType);
    }

    public String getMessage() {
        return baseExceptionType.getMessage();
    }

    public int getCode() {
        return baseExceptionType.getErrorCode();
    }

    public HttpStatus getHttpStatus() {
        return baseExceptionType.getHttpStatus();
    }
}
