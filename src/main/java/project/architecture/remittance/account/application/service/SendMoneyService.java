package project.architecture.remittance.account.application.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import project.architecture.remittance.account.application.port.in.SendMoneyCommand;
import project.architecture.remittance.account.application.port.in.SendMoneyUseCase;
import project.architecture.remittance.account.application.port.out.AccountLock;
import project.architecture.remittance.account.application.port.out.LoadAccountPort;
import project.architecture.remittance.account.application.port.out.UpdateAccountStatePort;
import project.architecture.remittance.account.application.service.exception.ThresholdExceededException;
import project.architecture.remittance.account.domain.Account;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Money;
import project.architecture.remittance.common.annotation.UseCase;
import project.architecture.remittance.common.exception.BusinessException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static project.architecture.remittance.account.application.service.exception.AccountTypeException.ACCOUNT_NOT_FOUND_EXCEPTION;

@Slf4j
@UseCase
@Service
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;

    public SendMoneyService(
            LoadAccountPort loadAccountPort,
            AccountLock accountLock,
            UpdateAccountStatePort updateAccountStatePort,
            MoneyTransferProperties moneyTransferProperties
    ) {
        this.loadAccountPort = loadAccountPort;
        this.accountLock = accountLock;
        this.updateAccountStatePort = updateAccountStatePort;
        this.moneyTransferProperties = moneyTransferProperties;
    }

    @Override
    @Transactional
    public boolean sendMoney(SendMoneyCommand command) {
        checkThreshold(command);

        LocalDateTime baselineDate = LocalDateTime.now();
        Account sourceAccount = loadAccountPort.loadAccount(
                command.getSourceAccountId(),
                baselineDate
        );
        log.info("Source: {}", sourceAccount);
        Account targetAccount = loadAccountPort.loadAccount(
                command.getTargetAccountId(),
                baselineDate
        );
        log.info("Target: {}", targetAccount);
        return !sendMoney(command, sourceAccount, targetAccount);
    }

    private boolean sendMoney(
            SendMoneyCommand command,
            Account sourceAccount,
            Account targetAccount
    ) {
        AccountId sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> BusinessException.of(ACCOUNT_NOT_FOUND_EXCEPTION));
        AccountId targetAccountId = targetAccount.getId()
                .orElseThrow(() -> BusinessException.of(ACCOUNT_NOT_FOUND_EXCEPTION));

        RLock sourceAccountLock = accountLock.lockAccount(sourceAccountId);
        RLock targetAccountLock = accountLock.lockAccount(targetAccountId);
        try {
            boolean available = sourceAccountLock.tryLock(3, 2, TimeUnit.SECONDS);

            if (!available) {
                Thread.sleep(1000);
            }
            sourceAccount.withdraw(command.getMoney(), targetAccountId);
            targetAccount.deposit(command.getMoney(), sourceAccountId);

            updateAccountStatePort.updateActivities(sourceAccount);
            updateAccountStatePort.updateActivities(targetAccount);
        } catch (InterruptedException e) {
            return false;
        } finally {
            if (sourceAccountLock.isLocked()) {
                accountLock.releaseAccount(sourceAccountLock);
            }
            if (targetAccountLock.isLocked()) {
                accountLock.releaseAccount(targetAccountLock);
            }
        }
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        Money maximumTransferThreshold = moneyTransferProperties.getMaximumTransferThreshold();
        if (command.getMoney().isGreaterThan(maximumTransferThreshold)) {
            throw new ThresholdExceededException(
                    moneyTransferProperties.getMaximumTransferThreshold(),
                    command.getMoney()
            );
        }
    }
}




