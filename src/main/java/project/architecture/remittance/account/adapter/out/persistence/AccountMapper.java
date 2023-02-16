package project.architecture.remittance.account.adapter.out.persistence;

import org.springframework.stereotype.Component;
import project.architecture.remittance.account.domain.Account;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Activity;
import project.architecture.remittance.account.domain.ActivityId;
import project.architecture.remittance.account.domain.ActivityWindow;
import project.architecture.remittance.account.domain.Money;

import java.util.ArrayList;
import java.util.List;

@Component
class AccountMapper {

    Account mapToDomainEntity(
            AccountJpaEntity account,
            List<ActivityJpaEntity> activities,
            Long withdrawalBalance,
            Long depositBalance
    ) {
        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance)
        );
        return Account.withId(
                new AccountId(account.getId()),
                baselineBalance,
                mapToActivityWindow(activities)
        );

    }

    ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = new ArrayList<>();

        for (ActivityJpaEntity activity : activities) {
            mappedActivities.add(
                    new Activity(
                            new ActivityId(activity.getId()),
                            new AccountId(activity.getOwnerAccountId()),
                            new AccountId(activity.getSourceAccountId()),
                            new AccountId(activity.getTargetAccountId()),
                            activity.getTimestamp(),
                            Money.of(activity.getAmount())
                    )
            );
        }

        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return new ActivityJpaEntity(
                activity.getId() == null ? null : activity.getId(),
                activity.getTimestamp(),
                activity.getOwnerAccountId().getValue(),
                activity.getSourceAccountId().getValue(),
                activity.getTargetAccountId().getValue(),
                activity.getMoney()
        );
    }

}
