package project.architecture.remittance.account.application.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.architecture.remittance.account.domain.Account;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.ActivityWindow;
import project.architecture.remittance.account.domain.Money;
import project.architecture.remittance.common.AccountTestData;
import project.architecture.remittance.common.ActivityTestData;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("계좌 도메인 테스트")
class AccountTest {

    @Test
    @DisplayName("잔액을 계산하면 그 결과를 알 수 있다.")
    void 잔액_계산_테스트() {
        AccountId accountId = new AccountId(1L);
        ActivityWindow activityWindow = new ActivityWindow(
                ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(1000L)).build()
        );
        Account account = AccountTestData.defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(500L))
                .withActivityWindow(activityWindow)
                .build();

        Money balance = account.calculateBalance();

        assertThat(balance).isEqualTo(Money.of(1500L));
    }

    @Test
    @DisplayName("금액을 인출하면 인출된 금액만큼 빠져나간다.")
    void 계좌_인출_테스트() {
        AccountId accountId = new AccountId(1L);
        ActivityWindow activityWindow = new ActivityWindow(
                ActivityTestData.defaultActivity()
                        .withTargetAccount(accountId)
                        .withMoney(Money.of(-1500L)).build()
        );
        Account account = AccountTestData.defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(1500L))
                .withActivityWindow(activityWindow)
                .build();

        boolean success = account.withdraw(Money.of(0L), new AccountId(1L));

        Assertions.assertAll(
                () -> assertThat(success).isTrue(),
                () -> Assertions.assertEquals(2, account.getActivityWindowSize()),
                () -> assertThat(account.calculateBalance()).isEqualTo(Money.of(0L))
        );
    }

    @Test
    @DisplayName("예금을 하면 그 금액만큼 계좌 액수가 증가한다.")
    void 예금_성공_테스트() {
        AccountId accountId = new AccountId(1L);
        Account account = AccountTestData.defaultAccount()
                .withAccountId(accountId)
                .withBaselineBalance(Money.of(500L))
                .withActivityWindow(new ActivityWindow(
                        ActivityTestData.defaultActivity()
                                .withTargetAccount(accountId)
                                .withMoney(Money.of(1000L)).build()))
                .build();

        boolean success = account.deposit(Money.of(500L), new AccountId(99L));

        assertThat(success).isTrue();
        assertThat(account.getActivityWindow().getActivities()).hasSize(2);
        assertThat(account.calculateBalance()).isEqualTo(Money.of(2000L));
    }
}
