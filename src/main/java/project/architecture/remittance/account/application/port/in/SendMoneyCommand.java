package project.architecture.remittance.account.application.port.in;

import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Money;
import project.architecture.remittance.common.validation.SelfValidating;

import java.util.Objects;

public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    private final AccountId sourceAccountId;
    private final AccountId targetAccountId;
    private final Money money;

    public SendMoneyCommand(
            AccountId sourceAccountId,
            AccountId targetAccountId,
            Money money
    ) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }

    public AccountId getSourceAccountId() {
        return sourceAccountId;
    }

    public AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public Money getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendMoneyCommand)) return false;
        SendMoneyCommand that = (SendMoneyCommand) o;
        return getSourceAccountId().equals(that.getSourceAccountId())
                && getTargetAccountId().equals(that.getTargetAccountId())
                && getMoney().equals(that.getMoney()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getSourceAccountId(),
                getTargetAccountId(),
                getMoney()
        );
    }
}
