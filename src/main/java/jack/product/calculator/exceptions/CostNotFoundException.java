package jack.product.calculator.exceptions;

public class CostNotFoundException extends RuntimeException {
    public CostNotFoundException(String message) {
        super(message);
    }
}
