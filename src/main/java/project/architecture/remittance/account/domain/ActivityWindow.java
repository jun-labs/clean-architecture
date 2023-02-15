package project.architecture.remittance.account.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ActivityWindow {

    private final List<Activity> activities;

    public ActivityWindow(List<Activity> activities) {
        if (Objects.isNull(activities)) {
            throw new IllegalArgumentException();
        }
        this.activities = activities;
    }

    public ActivityWindow(Activity... activities) {
        this.activities = new ArrayList<>(Arrays.asList(activities));
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(this.activities);
    }

    public int size() {
        return this.activities.size();
    }

    public LocalDateTime getStartTimestamp() {
        return activities.stream()
                .min(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    public LocalDateTime getEndTimestamp() {
        return activities.stream()
                .max(Comparator.comparing(Activity::getTimestamp))
                .orElseThrow(IllegalStateException::new)
                .getTimestamp();
    }

    public Money calculateBalance(AccountId accountId) {
        Money depositBalance = activities.stream()
                .filter(isEqualToTargetId(accountId))
                .map(Activity::getMoneyAsValue)
                .reduce(Money.ZERO, Money::add);
        Money withdrawalBalance = activities.stream()
                .filter(isEqualToSourceId(accountId))
                .map(Activity::getMoneyAsValue)
                .reduce(Money.ZERO, Money::add);
        return Money.add(depositBalance, withdrawalBalance.negate());
    }

    private Predicate<Activity> isEqualToTargetId(AccountId accountId) {
        return account -> account.getTargetAccountId().equals(accountId);
    }

    private Predicate<Activity> isEqualToSourceId(AccountId accountId) {
        return account -> account.getSourceAccountId().equals(accountId);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }
}
