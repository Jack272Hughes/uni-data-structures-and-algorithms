package jack.product.calculator;

import jack.product.calculator.parsing.CostsParser;
import jack.product.calculator.parsing.ProductsParser;
import jack.product.calculator.structures.HashMap;
import jack.product.calculator.structures.Node;

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

        return productCosts;
    }

    private int calculateProduct(Node product, HashMap<Integer> costs) {
        return 0;
    }
}
