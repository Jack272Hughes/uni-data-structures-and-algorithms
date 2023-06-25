package jack.product.calculator.structures;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class LinkedStack<T> {
    private Link<T> top;

    public void add(T value) {
        Link<T> newLink = new Link<>(value);
        if (top != null) top.setNext(newLink);
        top = newLink;
    }

    public Optional<T> pop() {
        T returnValue = top.getValue();

        top = top.getPrevious();
        if (top != null) top.setNext(null);

        return Optional.of(returnValue);
    }

    public Optional<T> peek() {
        return ofNullable(top).map(Link::getValue);
    }
}
