package project.architecture.remittance.account.application.service;

import project.architecture.remittance.account.domain.Money;

import java.util.Objects;

public class MoneyTransferProperties {

    private Money maximumTransferThreshold = Money.of(1_000_000L);

    protected MoneyTransferProperties() {
    }

    public MoneyTransferProperties(Money money) {
        this.maximumTransferThreshold = money;
    }

    public Money getMaximumTransferThreshold() {
        return maximumTransferThreshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoneyTransferProperties)) return false;
        MoneyTransferProperties that = (MoneyTransferProperties) o;
        return getMaximumTransferThreshold().equals(that.getMaximumTransferThreshold());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaximumTransferThreshold());
    }
}
