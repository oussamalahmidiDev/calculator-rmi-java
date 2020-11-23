package com.learn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
//import java.awt.*;

public class Controller {
    Operation operation;

    @FXML
    TextField field;

//    @FXML
//    Button b1, b2;


    @FXML
    public void initialize() {
        System.out.println("UI Displayed" + operation);
//        field.setText(operation.toString());
//        b1.setOnAction(this::onButtonClick);
    }

    public void onButtonClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        String text = source.getText();

        if (operation == null)
            operation = new Operation();

        if (text.matches("\\d")) {
            if (operation.getX() == null)
                operation.setX(Integer.valueOf(text));
            else operation.setY(Integer.valueOf(text));
        }
        else if (text.matches("[+/-x]")) {
            switch (text) {
                case "+": operation.setOperationType(Operation.OperationType.ADD); break;
                case "-": operation.setOperationType(Operation.OperationType.SUB); break;
                case "x": operation.setOperationType(Operation.OperationType.MULT); break;
                case "/": operation.setOperationType(Operation.OperationType.DIV); break;
            }
        } else if (text.equals("C"))
            operation = new Operation();

        System.out.println("Operation : " + operation);
        displayOperation();
    }

    public void displayOperation() {
        StringBuilder text = new StringBuilder();

        if (operation.getX() != null)
            text.append(operation.getX());

        if (operation.getX() != null && operation.getOperationType() != null)
            text.append(operation.getOperationType());

        if (operation.getX() != null && operation.getOperationType() != null && operation.getY() != null)
            text.append(operation.getY());

        field.setText(text.toString());
    }
}
