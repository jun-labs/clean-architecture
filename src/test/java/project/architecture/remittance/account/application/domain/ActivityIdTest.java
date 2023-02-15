package project.architecture.remittance.account.application.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import project.architecture.remittance.account.domain.AccountId;
import project.architecture.remittance.account.domain.ActivityId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("예금 활동 아이디 도메인 테스트")
class ActivityIdTest {

    @ParameterizedTest
    @ValueSource(longs = {0, -1, -2})
    @DisplayName("0이하의 잘못된 값이 들어오면 IllegalArgumentException을 발생시킨다.")
    void 예금_활동_아이디_생성_실패_테스트(Long parameter) {
        assertThatThrownBy(() -> new ActivityId(parameter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 아이디 값 입니다.");
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Null 값이 들어오면 IllegalArgumentException을 발생시킨다.")
    void 예금_활동_아이디_생성_실패_테스트_유스_케이스(Long parameter) {
        assertThatThrownBy(() -> new ActivityId(parameter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 값을 입력하지 않았습니다.");
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5})
    @DisplayName("계좌 아이디는 값으로 비교된다.")
    void 예금_활동_아이디_값_비교_테스트(Long parameter) {
        Assertions.assertEquals(new ActivityId(parameter), new ActivityId(parameter));
    }
}
