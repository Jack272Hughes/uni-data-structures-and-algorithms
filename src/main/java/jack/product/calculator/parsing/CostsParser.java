package jack.product.calculator.parsing;

import jack.product.calculator.exceptions.ParsingException;
import jack.product.calculator.structures.HashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;

public class CostsParser {
    // This regex matches a string, an equals sign, then a numerical value
    // allowing for zero or more spaces, before or after any of those three things
    // The key and value are in capture groups to be used by the hash map
    private static final Pattern KEY_VALUE_REGEX_PATTERN = Pattern.compile("^\\s*([\\w\\s]+?)\\s*=\\s*(\\d+)\\s*$");

    public HashMap<Integer> parse(String fileName) {
        File costsFile = ofNullable(getClass().getResource(fileName))
                .map(resource -> new File(resource.getPath()))
                .filter(File::isFile)
                .orElseThrow(() -> new ParsingException("'" + fileName + "' is not a valid file"));

        try (BufferedReader reader = new BufferedReader(new FileReader(costsFile))) {
            int lineNum = 0;
            HashMap<Integer> costsMap = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                if (line.isBlank()) continue;

                // Should match the entire line due to regex symbols ^ and $
                Matcher matcher = KEY_VALUE_REGEX_PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new ParsingException("Invalid cost value found at line " + lineNum);
                }

                String name = matcher.group(1);
                int cost = Integer.parseInt(matcher.group(2));
                costsMap.put(name, cost);
            }

            return costsMap;
        } catch (IOException e) {
            throw new ParsingException("Exception while reading costs file", e);
        }
    }
}
