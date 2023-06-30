package jack.product.calculator.structures;

import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class LinkedStack<T> {
    private Link<T> top;
    private int size = 0;

    public void add(T value) {
        Link<T> newLink = new Link<>(value);
        newLink.setNext(top);
        top = newLink;
        size++;
    }

    public void addAll(LinkedStack<T> otherStack) {
        Link<T> otherTop = otherStack.top;
        while (otherTop != null) {
            add(otherTop.value());
            otherTop = otherTop.next();
        }
    }

    public Optional<T> pop() {
        return ofNullable(top).stream()
                .peek(link -> {
                    top = link.next();
                    size--;
                })
                .map(Link::value)
                .findFirst();
    }

    public Optional<T> peek() {
        return ofNullable(top).map(Link::value);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedStack<?> that = (LinkedStack<?>) o;
        if (size != that.size) return false;

        Link<?> thisTop = top;
        Link<?> thatTop = that.top;
        while(thisTop != null && thatTop != null) {
            if (!Objects.equals(thisTop.value(), thatTop.value())) {
                return false;
            }
            thisTop = thisTop.next();
            thatTop = thatTop.next();
        }
        return Objects.equals(thisTop, thatTop);
    }
}
