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

    @Test
    void givenStackWithValues_sizeShouldReturnCorrectSize() {
        underTest.add(1);
        underTest.add(2);
        underTest.add(3);
        underTest.pop();
        assertThat(underTest.size()).isEqualTo(2);
    }

    @Test
    void givenEmptyStack_isEmptyShouldReturnTrue() {
        assertThat(underTest.isEmpty()).isTrue();
    }

    @Test
    void givenStackWithValues_isEmptyShouldReturnFalse() {
        underTest.add(1);
        assertThat(underTest.isEmpty()).isFalse();
    }

    @Test
    void givenTwoIdenticalStacks_equalsShouldReturnTrue() {
        LinkedStack<Integer> identicalStack = new LinkedStack<>();
        identicalStack.add(1);
        identicalStack.add(2);

        underTest.add(1);
        underTest.add(2);

        assertThat(underTest.equals(identicalStack)).isTrue();
    }

    @Test
    void givenTwoDifferentStacks_equalsShouldReturnFalse() {
        LinkedStack<Integer> differentStack = new LinkedStack<>();
        differentStack.add(1);
        differentStack.add(2);

        underTest.add(3);
        underTest.add(4);

        assertThat(underTest.equals(differentStack)).isFalse();
    }

    @Test
    void givenTwoStacksOfDifferentLengths_equalsShouldReturnFalse() {
        LinkedStack<Integer> differentStack = new LinkedStack<>();
        differentStack.add(1);

        underTest.add(2);
        underTest.add(1);

        assertThat(underTest.equals(differentStack)).isFalse();
    }
}