package jack.product.calculator.parsing;

import jack.product.calculator.structures.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class ProductsParser {

    public Node[] parse(String directoryName) {
        File[] files = ofNullable(getClass().getResource(directoryName))
                .map(resource -> new File(resource.getPath()))
                .map(File::listFiles)
                .orElseThrow(() -> new ParsingException("'" + directoryName + "' is not a valid directory"));

        return Stream.of(files)
                .map(this::parseFile)
                .toArray(Node[]::new);
    }

    private Node parseFile(File file) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return new Node("");
        } catch (IOException e) {
            throw new ParsingException("Exception while reading products file", e);
        }
    }
}
