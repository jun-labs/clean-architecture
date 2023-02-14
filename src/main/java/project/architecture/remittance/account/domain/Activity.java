package project.architecture.remittance.account.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Activity {

    private final ActivityId id;
    private final AccountId ownerAccountId;
    private final AccountId sourceAccountId;
    private final AccountId targetAccountId;
    private final LocalDateTime timestamp;
    private final Money money;

    public Activity(
            ActivityId activityId,
            AccountId ownerAccountId,
            AccountId sourceAccountId,
            AccountId targetAccountId,
            LocalDateTime timestamp,
            Money money
    ) {
        this.id = activityId;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public Activity(
            AccountId ownerAccountId,
            AccountId sourceAccountId,
            AccountId targetAccountId,
            LocalDateTime timestamp,
            Money money
    ) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public Long getId() {
        return !Objects.isNull(id) ? id.getValue() : null;
    }

    public AccountId getOwnerAccountId() {
        return ownerAccountId;
    }

    public AccountId getSourceAccountId() {
        return sourceAccountId;
    }

    public AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Money getMoneyAsValue() {
        return money;
    }

    public Long getMoney() {
        return money.getAmount().longValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return getId().equals(activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        assert id != null;
        return id.toString();
    }
}
