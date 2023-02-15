package project.architecture.remittance.account.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import project.architecture.remittance.account.domain.Money;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("금액 도메인 테스트")
class MoneyTest {

    @Test
    @DisplayName("금액을 더하면 그 금액이 반환된다.")
    void 금액_더하기_테스트() {
        Money money = Money.add(100L, 200L);
        assertEquals(new BigInteger("300"), money.getAmount());
    }

    @Test
    @DisplayName("금액을 빼면 그 금액이 반환된다.")
    void 금액_빼기_테스트() {
        Money money = Money.subtract(Money.of(300L), Money.of(100L));
        assertEquals(new BigInteger("200"), money.getAmount());
    }

    @ParameterizedTest
    @ValueSource(longs = {100L, 1000L, 30000L})
    @DisplayName("금액이 0원 이상인지 알 수 있다.")
    void 금액_액수_체크_테스트(Long parameter) {
        Money money = Money.of(parameter);
        Money zeroMoney = Money.of(0L);

        assertAll(
                () -> assertTrue(zeroMoney.isPositiveOrZero()),
                () -> assertTrue(money.isPositiveOrZero()),
                () -> assertTrue(money.isPositive()),
                () -> assertFalse(money.isNegative())
        );
    }
}
