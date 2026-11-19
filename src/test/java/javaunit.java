import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    @Test
    @DisplayName("1 더하기 2는 3이다")
    void additionTest() {
        int a = 1;
        int b = 2;
        Calculator calc = new Calculator();

        int result = calc.add(a, b);

        assertThat(result).isEqualTo(3);
    }

    @BeforeAll
    public static void prepareAll() {
        System.out.println("최초준비");
    }

    @AfterAll
    public static void cleanupAll() {
        System.out.println("최종마무리");
    }
}
