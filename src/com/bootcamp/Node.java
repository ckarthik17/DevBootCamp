package com.bootcamp;

import java.util.HashMap;
import java.util.Map;

import static com.bootcamp.Node.Operator.operatorEnum;

//Used to represent a expression
public class Node {
    private final String value;
    private Node left;
    private Node right;

    public Node(String value) {
        this.value = value;
    }

    public Node(Node left, String value, Node right) {
        this(value);
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return (left==null && right==null);
    }

    @Override
    public String toString() {
        if(isLeaf()) { return value; }
        return "(" + left.toString() + value + right.toString() + ")";
    }

    public String prefixNotation() {
        if(isLeaf()) { return value; };

        String leftExpression;
        String rightExpression;

        if(left.hasLowerPrecedence(value)) {
            leftExpression = "(" + left.prefixNotation() + ")";
        } else {
            leftExpression = left.prefixNotation();
        }

        if(right.hasLowerPrecedence(value)) {
            rightExpression = "(" + right.prefixNotation() + ")";
        } else {
            rightExpression = right.prefixNotation();
        }

        return value + leftExpression + rightExpression;
    }

    private boolean hasLowerPrecedence(String anotherValue) {
        if(this.isLeaf()) {
            return false;
        }
        return operatorEnum(this.value).priority < operatorEnum(anotherValue).priority;
    }

    public enum Operator {
        ADD("+", 0),
        SUBTRACT("-", 0),
        MULTIPLY("*", 1),
        DIVIDE("/", 1);
        private String symbol;
        private int priority;
        private static Map<String , Operator> operators = new HashMap<String, Operator>();

        private Operator(String symbol, int priority) {
            this.symbol = symbol;
            this.priority = priority;
        }

        public static Operator operatorEnum(String symbol) {
            return operators.get(symbol);
        }

        static {
            for(Operator operator : Operator.values()) {
                operators.put(operator.symbol, operator);
            }
        }
    }
}
