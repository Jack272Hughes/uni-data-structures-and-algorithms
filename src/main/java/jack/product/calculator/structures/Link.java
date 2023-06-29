package jack.product.calculator.structures;

public class Link<T> {
    private T value;
    private Link<T> next;

    public Link(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Link<T> next() {
        return next;
    }

    public void setNext(Link<T> next) {
        this.next = next;
    }
}
