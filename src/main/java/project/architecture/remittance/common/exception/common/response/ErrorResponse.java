package project.architecture.remittance.common.exception.common.response;

import org.springframework.http.HttpStatus;
import project.architecture.remittance.common.exception.BusinessException;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final LocalDateTime time;
    private final HttpStatus status;
    private final int code;
    private final String message;

    private ErrorResponse(BusinessException userExceptionType) {
        this.time = LocalDateTime.now();
        this.message = userExceptionType.getMessage();
        this.code = userExceptionType.getCode();
        this.status = userExceptionType.getHttpStatus();
    }

    public static ErrorResponse of(BusinessException businessException) {
        return new ErrorResponse(businessException);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
