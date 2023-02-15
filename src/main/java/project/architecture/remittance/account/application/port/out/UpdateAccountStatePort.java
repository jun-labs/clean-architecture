package project.architecture.remittance.account.application.port.out;

import project.architecture.remittance.account.domain.Account;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

}
