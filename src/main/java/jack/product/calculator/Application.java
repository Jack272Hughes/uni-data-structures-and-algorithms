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
        HashMap<Integer> costs = new Application().calculateProductCosts("/products", "/costs.txt");
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

    private int calculateProduct(Node product, HashMap<Integer> costs) {
        int currentTotal = 0;
        LinkedStack<Node> nodesToCheck = new LinkedStack<>();
        nodesToCheck.add(product);

        while (!nodesToCheck.isEmpty()) {
            Node node = nodesToCheck.pop().orElseThrow();

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
