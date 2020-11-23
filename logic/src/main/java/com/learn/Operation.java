package com.learn;

import java.io.Serializable;

public class Operation  implements Serializable {
    private final static long serialVersionUID = -1;


    private Integer x, y;
    private OperationType operationType;

    public Operation(int x, int y, OperationType operationType) {
        this.x = x;
        this.y = y;
        this.operationType = operationType;
    }

    public Operation() {
        x = 0;
        y = 0;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Integer calculate() {
        switch (operationType) {
            case ADD:
                return x + y;
            case SUB:
                return x - y;
            case MULT:
                return x * y;
            case DIV:
                return x / y;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        switch (operationType) {
            case ADD:
                return x + "+" + y;
            case SUB:
                return x + "-" + y;
            case MULT:
                return x + "x" + y;
            case DIV:
                return x + "/" + y;
            default:
                return x + operationType.toString() + y;
        }
    }

    enum OperationType {
        ADD,
        SUB,
        MULT,
        DIV
    }

}
