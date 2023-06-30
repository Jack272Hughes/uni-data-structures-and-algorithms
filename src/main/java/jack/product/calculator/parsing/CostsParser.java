package jack.product.calculator.parsing;

import jack.product.calculator.structures.HashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.util.Optional.ofNullable;

public class CostsParser {
    // This regex matches a string, an equals sign, then a numerical value
    // allowing for zero or more spaces, before or after any of those three things
    // The key and the value are in capture groups to be used by the hash map
    private static final String KEY_VALUE_REGEX = "\\s*([\\w\\s]+?)\\s*=\\s*(\\d)+\\s*$";

    public HashMap<Integer> parse(String fileName) {
        File costsFile = ofNullable(getClass().getResource(fileName))
                .map(resource -> new File(resource.getPath()))
                .filter(File::isFile)
                .orElseThrow(() -> new ParsingException("'" + fileName + "' is not a valid file"));

        try (BufferedReader reader = new BufferedReader(new FileReader(costsFile))) {
            return new HashMap<>();
        } catch (IOException e) {
            throw new ParsingException("Exception while reading costs file", e);
        }
    }
}
