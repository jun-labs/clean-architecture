package project.architecture.remittance.account.adapter.out.persistence;

import project.architecture.remittance.account.application.port.out.LoadAccountPort;
import project.architecture.remittance.account.application.port.out.UpdateAccountStatePort;
import project.architecture.remittance.account.domain.Account;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Activity;
import project.architecture.remittance.common.annotation.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@PersistenceAdapter
@RequiredArgsConstructor
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final SpringDataAccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(
            AccountId accountId,
            LocalDateTime baselineDate
    ) {
        AccountJpaEntity account = accountRepository.findById(accountId.getValue())
                .orElseThrow(EntityNotFoundException::new);
        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(
                accountId.getValue(),
                baselineDate
        );
        Long withdrawalBalance = orZero(activityRepository.getWithdrawalBalanceUntil(
                        accountId.getValue(),
                        baselineDate
                )
        );
        Long depositBalance = orZero(activityRepository.getDepositBalanceUntil(
                        accountId.getValue(),
                        baselineDate
                )
        );
        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance
        );
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public void updateActivities(Account account) {
        List<Activity> activities = account.getActivityWindow().getActivities();
        for (Activity activity : activities) {
            if (!Objects.isNull(activity.getId())) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

}
