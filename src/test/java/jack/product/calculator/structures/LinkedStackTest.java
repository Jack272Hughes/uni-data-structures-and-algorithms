package jack.product.calculator.structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedStackTest {
    private LinkedStack<Integer> underTest;

    @BeforeEach
    void beforeEach() {
        underTest = new LinkedStack<>();
    }

    @Test
    void givenEmptyStack_peekAndPopShouldReturnEmpty() {
        assertThat(underTest.peek()).isEmpty();
        assertThat(underTest.pop()).isEmpty();
    }

    @Test
    void givenEmptyStack_shouldBeAbleToAddPeekAndPopValue() {
        int expected = 5;
        underTest.add(expected);

        assertThat(underTest.peek()).hasValue(expected);
        assertThat(underTest.pop()).hasValue(expected);
    }

    @Test
    void givenStackWithValues_peekAndPopShouldReturnMostRecentlyAddedValue() {
        int expected = 5;
        underTest.add(1);
        underTest.add(expected);

        assertThat(underTest.peek()).hasValue(expected);
        assertThat(underTest.pop()).hasValue(expected);
    }

    @Test
    void givenEmptyStack_shouldBeAbleToAddMultipleValues_thenPopShouldRemoveThemInOrder() {
        underTest.add(3);
        underTest.add(2);
        underTest.add(1);

        for (int i = 1; i <= 3; i++) {
            assertThat(underTest.pop()).hasValue(i);
        }

        assertThat(underTest.peek()).isEmpty();
    }
}