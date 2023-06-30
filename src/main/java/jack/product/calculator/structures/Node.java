package jack.product.calculator.structures;

import java.util.Objects;

public class Node {
    private final String name;
    private final LinkedStack<Node> children = new LinkedStack<>();
    private Node parent;

    public Node(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Node parent() {
        return parent;
    }

    public LinkedStack<Node> children() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name) && Objects.equals(children, node.children);
    }
}
