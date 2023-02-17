package project.architecture.remittance.account.application.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import project.architecture.remittance.account.application.port.out.AccountLock;
import project.architecture.remittance.account.domain.AccountId;

import javax.transaction.Transactional;

@Slf4j
@Component
class NoOpAccountLock implements AccountLock {

    private final RedissonClient redissonClient;

    public NoOpAccountLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    @Transactional
    public RLock lockAccount(AccountId accountId) {
        log.info("accountId: {}", accountId);
        return redissonClient.getLock(getKey(accountId));
    }

    @Override
    @Transactional
    public void releaseAccount(RLock rLock) {
        log.info("rLock: {}", rLock);
        rLock.unlock();
    }

    @Override
    public String getKey(AccountId accountId) {
        return String.format("#account-%s", accountId.toString());
    }
}
