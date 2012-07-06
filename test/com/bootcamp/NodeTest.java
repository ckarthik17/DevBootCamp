package com.bootcamp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    @Test
    public void shouldCreateALeafNode() {
        Node leafNode = new Node("5");
        assertEquals("5", leafNode.toString());
        assertTrue(leafNode.isLeaf());
    }

    @Test
    public void shouldCreateANodeOfExpression() {
        Node expression = new Node(new Node("1"), "+", new Node("2"));

        assertEquals("(1+2)", expression.toString());
    }

    @Test
    public void shouldCreateABiggerExpression() {
        Node subExpression = new Node(new Node("1"), "+", new Node("2"));
        Node expression = new Node(subExpression, "*", new Node("3"));

        assertEquals("((1+2)*3)", expression.toString());
    }

    @Test
    public void shouldDifferentiateTwoExpressions() {
        Node subExpression1 = new Node(new Node("1"), "+", new Node("2"));
        Node expression1 = new Node(subExpression1, "*", new Node("3"));

        Node subExpression2 = new Node(new Node("2"), "*", new Node("3"));
        Node expression2 = new Node(new Node("1"), "+", subExpression2);

        assertEquals("((1+2)*3)", expression1.toString());
        assertEquals("(1+(2*3))", expression2.toString());
    }

    @Test
    public void shouldReturnThePrefixNotationOfAExpression() {
        Node subExpression1 = new Node(new Node("1"), "+", new Node("2"));
        Node subExpression2 = new Node(new Node("3"), "+", new Node("8"));

        Node expression1 = new Node(subExpression1, "*", new Node("3"));
        Node expression2 = new Node(subExpression1, "*", subExpression2);
        // ((1+2)*3)    *+123
        assertEquals("*(+12)3", expression1.prefixNotation());

        // ((1+2)*(3+8))    *(+12)(+38)
        assertEquals("*(+12)(+38)", expression2.prefixNotation());
    }
}
