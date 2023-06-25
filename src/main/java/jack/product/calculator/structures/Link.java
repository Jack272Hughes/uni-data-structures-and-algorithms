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

    public Link<T> getPrevious() {
        return previous;
    }

    private void setPrevious(Link<T> previous) {
        this.previous = previous;
    }

    public Link<T> getNext() {
        return next;
    }

    public void setNext(Link<T> link) {
        if (link != null) link.setPrevious(this);
        next = link;
    }

    public Link<T> next() {
        return next;
    }
}
