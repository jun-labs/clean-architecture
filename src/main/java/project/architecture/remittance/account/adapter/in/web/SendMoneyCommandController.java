package project.architecture.remittance.account.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.architecture.remittance.account.adapter.in.web.response.SendMoneyResponse;
import project.architecture.remittance.account.application.port.in.SendMoneyCommand;
import project.architecture.remittance.account.application.port.in.SendMoneyUseCase;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.Money;
import project.architecture.remittance.common.annotation.WebAdapter;

@WebAdapter
@RestController
@RequiredArgsConstructor
class SendMoneyCommandController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping(path = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    ResponseEntity<SendMoneyResponse> sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Long amount
    ) {
        SendMoneyCommand command = new SendMoneyCommand(
                new AccountId(sourceAccountId),
                new AccountId(targetAccountId),
                Money.of(amount)
        );
        SendMoneyResponse response = SendMoneyResponse.of(sendMoneyUseCase.sendMoney(command));
        return ResponseEntity.ok(response);
    }
}
