package jack.product.calculator.parsing;

import jack.product.calculator.structures.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CostsParserTest {
    private CostsParser underTest;

    @BeforeEach
    void beforeEach() {
        underTest = new CostsParser();
    }

    @Test
    void givenValidFile_shouldParseIntoHashMapOfCosts() {
        HashMap<Integer> expectedMap = new HashMap<>();
        expectedMap.put("item0", 5);
        expectedMap.put("item1", 10);

        HashMap<Integer> actualMap = underTest.parse("/costs/valid.txt");
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void givenValidFileWithExtraSpacing_shouldTrimLines() {
        HashMap<Integer> expectedMap = new HashMap<>();
        expectedMap.put("item0", 5);
        expectedMap.put("item1", 10);
        expectedMap.put("item2", 7);

        HashMap<Integer> actualMap = underTest.parse("/costs/valid-spacing.txt");
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/costs/invalid-value.txt", "/costs/empty-value.txt"})
    void givenFileWithInvalidValue_shouldThrowException(String file) {
        assertThatThrownBy(() -> underTest.parse(file))
                .isInstanceOf(ParsingException.class)
                .hasMessage("Invalid cost value found at line 2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/", "/null.txt"})
    void givenInvalidFile_shouldThrowException(String file) {
        assertThatThrownBy(() -> underTest.parse(file))
                .isInstanceOf(ParsingException.class)
                .hasMessage("'" + file + "' is not a valid file");
    }
}