package project.architecture.remittance.account.application.port.in;

public interface SendMoneyUseCase {

	boolean sendMoney(SendMoneyCommand command);

}
