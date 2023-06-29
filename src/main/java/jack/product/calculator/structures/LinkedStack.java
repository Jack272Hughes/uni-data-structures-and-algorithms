package jack.product.calculator.structures;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class LinkedStack<T> {
    private Link<T> top;

    public void add(T value) {
        Link<T> newLink = new Link<>(value);
        newLink.setNext(top);
        top = newLink;
    }

    public Optional<T> pop() {
        return ofNullable(top).stream()
                .peek(link -> top = link.getNext())
                .map(Link::getValue)
                .findFirst();
    }

    public Optional<T> peek() {
        return ofNullable(top).map(Link::getValue);
    }
}
