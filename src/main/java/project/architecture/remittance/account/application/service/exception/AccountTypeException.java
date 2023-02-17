package project.architecture.remittance.account.application.service.exception;

import org.springframework.http.HttpStatus;
import project.architecture.remittance.common.exception.BaseExceptionType;

public enum AccountTypeException implements BaseExceptionType {
    THTRESHOLD_EXCEEDED_EXCEPTION(400, "최대 송금 가능한 계좌 금액을 초과했습니다.", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND_EXCEPTION(404, "계좌를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    AccountTypeException(
            int code,
            String message,
            HttpStatus httpStatus
    ) {
        this.code = code;
        this.message = message;
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
