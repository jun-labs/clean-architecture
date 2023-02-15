package project.architecture.remittance.account.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.ActivityWindow;
import project.architecture.remittance.account.domain.Money;
import project.architecture.remittance.common.ActivityTestData;

@DisplayName("에금 활동 도메인 테스트")
class ActivityWindowTest {

    @Test
    @DisplayName("에금 활동을 하면 활동을 한 금액이 계좌에 남는다.")
    void 예금_계산_테스트() {
        AccountId account1 = new AccountId(1L);
        AccountId account2 = new AccountId(2L);

        ActivityWindow window = new ActivityWindow(
                // Account1 -> Account2
                ActivityTestData.defaultActivity()
                        .withSourceAccount(account1)
                        .withTargetAccount(account2)
                        .withMoney(Money.of(1000L)).build(),
                // Account2 -> Account1
                ActivityTestData.defaultActivity()
                        .withSourceAccount(account2)
                        .withTargetAccount(account1)
                        .withMoney(Money.of(500)).build()
        );

        Assertions.assertThat(window.calculateBalance(account1)).isEqualTo(Money.of(-500));
        Assertions.assertThat(window.calculateBalance(account2)).isEqualTo(Money.of(500));
    }
}
