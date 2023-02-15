package project.architecture.remittance.account.application.port.out;

import project.architecture.remittance.account.domain.Account;
import project.architecture.remittance.account.domain.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {

	Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
