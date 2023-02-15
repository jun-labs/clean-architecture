package project.architecture.remittance.account.application.port.in;

import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Money;

public interface GetAccountBalanceQuery {

	Money getAccountBalance(AccountId accountId);

}
