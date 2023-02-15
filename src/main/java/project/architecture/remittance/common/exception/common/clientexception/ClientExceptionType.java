package project.architecture.remittance.common.exception.common.clientexception;

import org.springframework.http.HttpStatus;
import project.architecture.remittance.common.exception.BaseExceptionType;

public enum ClientExceptionType implements BaseExceptionType {

    INVALID_COOKIE("쿠키 값이 임의로 변경되었습니다", 400, HttpStatus.BAD_REQUEST),
    BAD_REQUEST("잘못된 요청입니다.", 400, HttpStatus.BAD_REQUEST),
    TOO_MANY_REQUESTS("너무 많은 요청을 보냈습니다.", 429, HttpStatus.TOO_MANY_REQUESTS);

    private final String message;
    private final int code;
    private final HttpStatus httpStatus;

    ClientExceptionType(String message,
                        int code,
                        HttpStatus httpStatus
    ) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
