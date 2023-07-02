package jack.product.calculator;

import jack.product.calculator.exceptions.CostNotFoundException;
import jack.product.calculator.parsing.CostsParser;
import jack.product.calculator.parsing.ProductsParser;
import jack.product.calculator.structures.HashMap;
import jack.product.calculator.structures.LinkedStack;
import jack.product.calculator.structures.Node;

import java.util.Optional;

public class Application {
    private final ProductsParser productsParser = new ProductsParser();
    private final CostsParser costsParser = new CostsParser();

    public static void main(String[] args) {
        String productsDirectory = args[0];
        String costsFile = args[1];

        HashMap<Integer> costs = new Application().calculateProductCosts(productsDirectory, costsFile);
        System.out.println(costs);
    }

    public HashMap<Integer> calculateProductCosts(String productsDirectory, String costsFile) {
        Node[] products = productsParser.parse(productsDirectory);
        HashMap<Integer> costs = costsParser.parse(costsFile);

        HashMap<Integer> productCosts = new HashMap<>();

        for (Node product : products) {
            int productCost = calculateProduct(product, costs);
            productCosts.put(product.name(), productCost);
        }

        return productCosts;
    }

    public int calculateProduct(Node product, HashMap<Integer> costs) {
        LinkedStack<Node> nodesToCheck = new LinkedStack<>();
        nodesToCheck.add(product);

        int currentTotal = 0;
        while (!nodesToCheck.isEmpty()) {
            Node node = nodesToCheck.pop().orElseThrow();

            // If an item has an associated cost it should
            // not include its children's cost in the total
            Optional<Integer> optionalCost = costs.get(node.name());
            if (optionalCost.isPresent()) {
                currentTotal += optionalCost.get();
                continue;
            }

            if (node.children().isEmpty()) {
                throw new CostNotFoundException("Unable to find cost for " + node.name());
            }

            nodesToCheck.addAll(node.children());
        }

        return currentTotal;
    }
}
