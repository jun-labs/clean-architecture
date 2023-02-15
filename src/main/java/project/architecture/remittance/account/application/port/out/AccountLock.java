package project.architecture.remittance.account.application.port.out;

import org.redisson.api.RLock;
import project.architecture.remittance.account.domain.AccountId;

public interface AccountLock {

    RLock lockAccount(AccountId accountId);

    void releaseAccount(RLock rLock);

    String getKey(AccountId accountId);
}
