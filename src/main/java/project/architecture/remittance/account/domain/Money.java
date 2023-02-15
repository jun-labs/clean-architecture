package project.architecture.remittance.account.domain;

import java.math.BigInteger;
import java.util.Objects;

public class Money {

    public static Money ZERO = Money.of(0L);

    private final BigInteger amount;

    private Money(long amount) {
        this.amount = BigInteger.valueOf(amount);
    }

    private Money(BigInteger amount) {
        validateMoney(amount);
        this.amount = amount;
    }

    private void validateMoney(BigInteger amount) {
        if (Objects.isNull(amount)) {
            throw new IllegalArgumentException("금액을 입력해주세요.");
        }
    }

    public static Money of(long value) {
        return new Money(BigInteger.valueOf(value));
    }

    public BigInteger getAmount() {
        return amount;
    }

    public boolean isPositiveOrZero() {
        return this.amount.compareTo(BigInteger.ZERO) >= 0;
    }

    public boolean isNegative() {
        return this.amount.compareTo(BigInteger.ZERO) < 0;
    }

    public boolean isPositive() {
        return this.amount.compareTo(BigInteger.ZERO) > 0;
    }

    public boolean isGreaterThanOrEqualTo(Money money) {
        return this.amount.compareTo(money.amount) >= 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount.compareTo(money.amount) >= 1;
    }

    public static Money add(Long a, Long b) {
        return new Money(a + b);
    }

    public static Money add(Money a, Money b) {
        return new Money(a.amount.add(b.amount));
    }

    public Money minus(Money money) {
        return new Money(this.amount.subtract(money.amount));
    }

    public Money plus(Money money) {
        return new Money(this.amount.add(money.amount));
    }

    public static Money subtract(Money a, Money b) {
        return new Money(a.amount.subtract(b.amount));
    }

    public Money negate() {
        return new Money(this.amount.negate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}
