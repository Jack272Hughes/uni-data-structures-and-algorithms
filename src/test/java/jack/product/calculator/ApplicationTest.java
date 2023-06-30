package jack.product.calculator;

import jack.product.calculator.exceptions.CostNotFoundException;
import jack.product.calculator.exceptions.ParsingException;
import jack.product.calculator.structures.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest {
    private static final String COSTS_FILE = "/component-test/costs.txt";

    private Application underTest;

    @BeforeEach
    void beforeEach() {
        underTest = new Application();
    }

    @Test
    void givenValidProduct_shouldReturnHashMapOfProductCosts() {
        HashMap<Integer> expectedCosts = new HashMap<>();
        // The expected cost is item1 + item3 + item4 + item3
        // item6 should be ignored because its parent (item3) already has a cost
        expectedCosts.put("product", 121);

        HashMap<Integer> actualCosts = underTest.calculateProductCosts("/component-test/valid", COSTS_FILE);
        assertThat(actualCosts).isEqualTo(expectedCosts);
    }

    @Test
    void givenFileMissingCost_shouldThrowError() {
        assertThatThrownBy(() -> underTest.calculateProductCosts("/component-test/missing-values", COSTS_FILE))
                .isInstanceOf(CostNotFoundException.class)
                .hasMessage("Unable to find cost for item10");
    }

    @Test
    void givenParsingExceptionIsThrownByProductsParser_shouldPropagateError() {
        assertThatThrownBy(() -> underTest.calculateProductCosts("/invalid", COSTS_FILE))
                .isInstanceOf(ParsingException.class);
    }

    @Test
    void givenParsingExceptionIsThrownByCostsParser_shouldPropagateError() {
        assertThatThrownBy(() -> underTest.calculateProductCosts("/component-test/valid", "/costs/invalid-value.txt"))
                .isInstanceOf(ParsingException.class);
    }
}