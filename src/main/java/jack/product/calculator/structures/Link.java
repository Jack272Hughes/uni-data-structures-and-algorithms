package jack.product.calculator.structures;

public class Link<T> {
    private T value;
    private Link<T> previous;
    private Link<T> next;

    public Link(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Link<T> getNext() {
        return next;
    }

    public void setNext(Link<T> link) {
        next = link;
    }

    public Link<T> next() {
        return next;
    }
}
