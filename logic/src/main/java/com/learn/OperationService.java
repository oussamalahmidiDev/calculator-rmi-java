package com.learn;

public class OperationService {

    public static Integer calculate(Operation operation) {
        switch (operation.getOperationType()) {
            case ADD:
                return operation.getX() + operation.getY();
            case SUB:
                return operation.getX() - operation.getY();
            case MULT:
                return operation.getX() * operation.getY();
            case DIV:
                return operation.getX() / operation.getY();
            default:
                return 0;
        }
    }
}
