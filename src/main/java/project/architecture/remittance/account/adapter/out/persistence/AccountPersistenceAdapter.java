package project.architecture.remittance.account.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.architecture.remittance.account.application.port.out.LoadAccountPort;
import project.architecture.remittance.account.application.port.out.UpdateAccountStatePort;
import project.architecture.remittance.account.domain.Account;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Activity;
import project.architecture.remittance.common.annotation.PersistenceAdapter;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@PersistenceAdapter
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final SpringDataAccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    public AccountPersistenceAdapter(
            SpringDataAccountRepository accountRepository,
            ActivityRepository activityRepository,
            AccountMapper accountMapper
    ) {
        this.accountRepository = accountRepository;
        this.activityRepository = activityRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account loadAccount(
            AccountId accountId,
            LocalDateTime baselineDate
    ) {
        AccountJpaEntity account = accountRepository.findById(accountId.getValue())
                .orElseThrow(EntityNotFoundException::new);
        List<ActivityJpaEntity> activities = activityRepository.findByOwnerAccountIdAndTimestamp(
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
        log.info("인출: {}", withdrawalBalance);
        log.info("입금: {}", depositBalance);
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
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

}
