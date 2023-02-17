package project.architecture.remittance.account.application.service;

import project.architecture.remittance.account.application.port.in.GetAccountBalanceQuery;
import project.architecture.remittance.account.application.port.out.LoadAccountPort;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Money;

import java.time.LocalDateTime;

class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    public GetAccountBalanceService(LoadAccountPort loadAccountPort) {
        this.loadAccountPort = loadAccountPort;
    }

    @Override
    public Money getAccountBalance(AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
                .calculateBalance();
    }
}
