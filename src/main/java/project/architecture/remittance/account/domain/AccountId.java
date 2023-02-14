package project.architecture.remittance.account.domain;

import java.util.Objects;

public class AccountId {

    private final Long value;

    public AccountId(Long id) {
        validateId(id);
        this.value = id;
    }

    private void validateId(Long value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("아이디 값을 입력하지 않았습니다.");
        }
        if (value <= 0L) {
            throw new IllegalArgumentException("올바르지 않은 아이디 값 입니다.");
        }
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId accountId = (AccountId) o;
        return getValue().equals(accountId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
