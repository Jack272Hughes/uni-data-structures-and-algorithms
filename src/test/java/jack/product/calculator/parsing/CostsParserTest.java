package jack.product.calculator.parsing;

import jack.product.calculator.exceptions.ParsingException;
import jack.product.calculator.structures.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CostsParserTest {
    private static final String BASE_PATH = "src/test/resources/costs";
    private static final String CURRENT_DIR = System.getProperty("user.dir") + "/";

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

        HashMap<Integer> actualMap = underTest.parse(BASE_PATH + "/valid.txt");
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void givenValidFileWithExtraSpacing_shouldTrimLines() {
        HashMap<Integer> expectedMap = new HashMap<>();
        expectedMap.put("item0", 5);
        expectedMap.put("item1", 10);
        expectedMap.put("item2", 7);

        HashMap<Integer> actualMap = underTest.parse(BASE_PATH + "/valid-spacing.txt");
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/invalid-value.txt", "/empty-value.txt"})
    void givenFileWithInvalidValue_shouldThrowException(String file) {
        assertThatThrownBy(() -> underTest.parse(BASE_PATH + file))
                .isInstanceOf(ParsingException.class)
                .hasMessage("Invalid cost value found at line 2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "/null.txt"})
    void givenInvalidFile_shouldThrowException(String file) {
        assertThatThrownBy(() -> underTest.parse(BASE_PATH + file))
                .isInstanceOf(ParsingException.class)
                .hasMessage("'" + CURRENT_DIR + BASE_PATH + file + "' is not a valid file");
    }
}