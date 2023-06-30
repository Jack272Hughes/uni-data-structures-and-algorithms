package jack.product.calculator.parsing;

import jack.product.calculator.structures.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class ProductsParser {
    private static final String INDENT = "\\s{4}";
    private static final String EXTENSION = ".txt";

    public Node[] parse(String directoryName) {
        // Optionals used because some of these methods can return null values if files don't exist
        File[] files = ofNullable(getClass().getResource(directoryName))
                .map(resource -> new File(resource.getPath()))
                .map(File::listFiles)
                .orElseThrow(() -> new ParsingException("'" + directoryName + "' is not a valid directory"));

        return Stream.of(files)
                .filter(file -> file.getName().endsWith(EXTENSION))
                .map(this::tryParseFile)
                .toArray(Node[]::new);
    }

    private Node tryParseFile(File file) {
        // try-with-resources statement used to close file after reading
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return parseFile(reader, file.getName().replace(EXTENSION, ""));
        } catch (IOException e) {
            throw new ParsingException("Exception while reading products file", e);
        }
    }

    private Node parseFile(BufferedReader reader, String productName) throws IOException {
        int indent = 0;
        int lineNum = 0;
        Node productNode = new Node(productName.trim());
        Node currentNode = productNode;

        String line;
        while ((line = reader.readLine()) != null) {
            lineNum++;
            if (line.isBlank()) continue;

            // Remove trailing strings because we don't care about indents after the item name
            String[] lineSplitByIndent = line.stripTrailing().split(INDENT, -1);
            int lineIndent = lineSplitByIndent.length - 1;
            String nodeName = lineSplitByIndent[lineIndent];

            if (lineIndent > indent) {
                // Should only be allowed to go up one indent at a time and, if the first line of the file
                // is indented, the product node would be the current node and would have no children yet
                if (lineIndent != indent + 1 || currentNode.children().isEmpty()) {
                    throw new ParsingException("'" + productName + EXTENSION + "' has invalid indentation at line " + lineNum);
                }
                indent++;
                currentNode = currentNode.children().peek().orElseThrow();
            } else {
                // Can go down any amount of indents since an item node will always have a parent
                while (lineIndent < indent) {
                    indent--;
                    currentNode = currentNode.parent();
                }
            }

            Node child = new Node(nodeName.trim());
            currentNode.addChild(child);
        }

        return productNode;
    }
}
