package jack.product.calculator.structures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NodeTest {

    @Test
    void givenNode_addChildShouldAddToChildrenAndUpdateParent() {
        Node node = new Node("parent");
        Node child = new Node("child");

        node.addChild(child);

        assertThat(node.children().peek()).contains(child);
        assertThat(child.parent()).isEqualTo(node);
    }

    @Test
    void givenTwoIdenticalNodes_equalsShouldReturnTrue() {
        String nodeName = "node1";
        Node node = createNode(nodeName);
        Node identicalNode = createNode(nodeName);

        assertThat(node.equals(identicalNode)).isTrue();
    }

    private Node createNode(String name) {
        Node node = new Node(name);
        Node nodeChild = new Node("node2");

        node.addChild(nodeChild);
        node.addChild(new Node("node3"));
        nodeChild.addChild(new Node("node4"));

        return node;
    }
}