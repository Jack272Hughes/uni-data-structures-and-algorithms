package jack.product.calculator.parsing;

import jack.product.calculator.structures.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductsParserTest {
    private ProductsParser underTest;

    @BeforeEach
    void beforeEach() {
        underTest = new ProductsParser();
    }

    @Test
    void givenSingleFile_parseShouldReturnCorrectlyParsedTree() {
        Node expectedProduct = new Node("first-product");
        Node[] nodes = createNodes(6);

        expectedProduct.addChild(nodes[0]);
        expectedProduct.addChild(nodes[4]);
        nodes[0].addChild(nodes[1]);
        nodes[0].addChild(nodes[2]);
        nodes[2].addChild(nodes[3]);
        nodes[4].addChild(nodes[5]);

        Node[] parsedNodes = underTest.parse("/single");
        assertThat(parsedNodes).containsExactly(expectedProduct);
    }

    @Test
    void givenMultipleFiles_parseShouldReturnMultipleNodes() {
        Node firstExpectedProduct = new Node("first-product");
        Node[] nodes = createNodes(4);
        firstExpectedProduct.addChild(nodes[0]);
        nodes[0].addChild(nodes[1]);
        nodes[0].addChild(nodes[2]);
        nodes[2].addChild(nodes[3]);

        Node secondExpectedProduct = new Node("second-product");
        nodes = createNodes(3);
        secondExpectedProduct.addChild(nodes[0]);
        secondExpectedProduct.addChild(nodes[2]);
        nodes[0].addChild(nodes[1]);

        Node[] parsedNodes = underTest.parse("/multiple");
        assertThat(parsedNodes).containsExactly(firstExpectedProduct, secondExpectedProduct);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/invalid", "/null"})
    void givenInvalidDirectory_shouldThrowException(String directory) {
        assertThatThrownBy(() -> underTest.parse(directory))
                .isInstanceOf(ParsingException.class)
                .hasMessage("'" + directory + "' is not a valid directory");
    }

    @ParameterizedTest
    @CsvSource({"/invalid-indent, 2", "/invalid-start-indent, 1"})
    void givenFileWithInvalidIndentation_shouldThrowException(String directory, int expectedLineNumber) {
        assertThatThrownBy(() -> underTest.parse(directory))
                .isInstanceOf(ParsingException.class)
                .hasMessage("'" + directory + "/test.txt' has invalid indentation at line " + expectedLineNumber);
    }

    private Node[] createNodes(int total) {
        Node[] nodes = new Node[total];
        for (int i = 0; i < total; i++) {
            nodes[i] = new Node("item" + i);
        }
        return nodes;
    }
}