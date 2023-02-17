package project.architecture.remittance.account.application.service.exception;

import project.architecture.remittance.account.domain.Money;
import project.architecture.remittance.common.exception.BaseExceptionType;

public class ThresholdExceededException extends RuntimeException {

    private final BaseExceptionType baseExceptionType;

    public ThresholdExceededException(Money threshold, Money actual) {
        super(String.format("최대 송금 금액을 초과: tried to transfer %s but threshold is %s!", actual, threshold));
        this.baseExceptionType = AccountTypeException.THTRESHOLD_EXCEEDED_EXCEPTION;
    }

    public BaseExceptionType getBaseExceptionType() {
        return baseExceptionType;
    }
}
