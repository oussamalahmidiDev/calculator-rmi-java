package com.learn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main extends Application {

    VBox main = new VBox();

    Operation operation = new Operation();

    TextField inputField, resultField;

    boolean currencyCalculatorToggle = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculatrice");

        main.setPadding(new Insets(10, 10, 10, 10));

        if (currencyCalculatorToggle)
            currencyCalculator();
        else standardCalculator();

        primaryStage.setScene(new Scene(main, 500, 200));
        primaryStage.show();

    }

    public void handleCalculeEvent(ActionEvent event) {
        String value = inputField.getText().replace(" ", "");
        Pattern operationPattern = Pattern.compile("(\\d+)([+/*x\\-])(\\d+)");
        Matcher operationMatcher = operationPattern.matcher(value);
        boolean matchFound = false;
        while (operationMatcher.find()) {
            operation.setX(Integer.valueOf(operationMatcher.group(1)));
            operation.setY(Integer.valueOf(operationMatcher.group(3)));

            switch (operationMatcher.group(2)) {
                case "+":
                    operation.setOperationType(Operation.OperationType.ADD);
                    break;
                case "-":
                    operation.setOperationType(Operation.OperationType.SUB);
                    break;
                case "x":
                case "*":
                    operation.setOperationType(Operation.OperationType.MULT);
                    break;
                case "/":
                    operation.setOperationType(Operation.OperationType.DIV);
                    break;
            }
            matchFound = true;
        }

        if (matchFound) {
            SocketConnection<Operation, Integer> socketConnection = new SocketConnection<>(operation);
            Thread socketConnectionThread = new Thread(socketConnection);
            socketConnectionThread.start();

            try {
                socketConnectionThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Recieved result : " + socketConnection.result);

            resultField.setText(socketConnection.result.toString());
        } else resultField.setText("Operation invalide");

    }

    public void standardCalculator() {

        Label title = new Label("Calculatrice");
        title.setFont(new Font(24.0));
        VBox.setMargin(title, new Insets(10, 10, 10, 10));

        Separator separator = new Separator();
        VBox.setMargin(separator, new Insets(0, 10, 5, 10));

        HBox container = new HBox();
        VBox.setMargin(container, new Insets(0, 10, 5, 10));

        inputField = new TextField();
        inputField.setPromptText("Entrer l'operation");
        inputField.setPrefHeight(40.0);
        HBox.setMargin(inputField, new Insets(0, 5, 0, 0));
        HBox.setHgrow(inputField, Priority.ALWAYS);
        inputField.setOnAction(this::handleCalculeEvent);

        Button calculButton = new Button("Calculer");
        HBox.setHgrow(calculButton, Priority.ALWAYS);
        calculButton.setPrefHeight(40.0);
        calculButton.setPrefWidth(100.0);
        calculButton.setOnAction(this::handleCalculeEvent);


        container.getChildren().addAll(inputField, calculButton);

        resultField = new TextField();
        resultField.setEditable(false);
        resultField.setPrefHeight(40.0);
        VBox.setMargin(resultField, new Insets(0, 10, 10, 10));

        Label switchToCurrencyLink = new Label("Afficher la calc de change");
        switchToCurrencyLink.setStyle("color: #0f86ff; -fx-cursor: pointer;");
        switchToCurrencyLink.applyCss();
        VBox.setMargin(switchToCurrencyLink, new Insets(0, 10, 0, 10));
        switchToCurrencyLink.setOnMouseClicked(e -> {
            currencyCalculator();
            switchToCurrencyLink.setVisible(false);
            System.out.println("switch clicked");
        });

        main.getChildren().clear();
        main.getChildren().addAll(title, separator, container, resultField, switchToCurrencyLink);
    }

    public void currencyCalculator() {
        Label title = new Label("Currency");
        title.setFont(new Font(24.0));

        VBox.setMargin(title, new Insets(10, 10, 10, 10));

        Separator separator = new Separator();
        VBox.setMargin(separator, new Insets(0, 10, 5, 10));

        HBox container = new HBox();
        VBox.setMargin(container, new Insets(0, 10, 5, 10));

        inputField = new TextField();
        inputField.setPromptText("Entrer le montant");
        inputField.setPrefHeight(40.0);
        HBox.setMargin(inputField, new Insets(0, 5, 0, 0));
        HBox.setHgrow(inputField, Priority.ALWAYS);

        ChoiceBox<String> currencyA = new ChoiceBox<>();
        currencyA.setPrefHeight(40.0);
        currencyA.getItems().addAll("MAD", "USD", "EUR");
        currencyA.setValue("MAD");
        HBox.setMargin(currencyA, new Insets(0, 5, 0, 0));

        ChoiceBox<String> currencyB = new ChoiceBox<>();
        currencyB.setPrefHeight(40.0);
        currencyB.setValue("EUR");
        currencyB.getItems().addAll("MAD", "USD", "EUR");
        HBox.setMargin(currencyB, new Insets(0, 5, 0, 0));


        Button calculButton = new Button("Calculer");
        HBox.setHgrow(calculButton, Priority.ALWAYS);
        calculButton.setPrefHeight(40.0);
        calculButton.setPrefWidth(100.0);
        calculButton.setOnAction(e -> {
            try {
                handleCurrencyChangeCalc(Double.parseDouble(inputField.getText()), Conversion.Currency.valueOf(currencyA.getValue()), Conversion.Currency.valueOf(currencyB.getValue()));
            } catch (NumberFormatException ex) {
                resultField.setText("Valeur invalide");
            }
        });
        inputField.setOnAction(e -> {
            try {
                handleCurrencyChangeCalc(Double.parseDouble(inputField.getText()), Conversion.Currency.valueOf(currencyA.getValue()), Conversion.Currency.valueOf(currencyB.getValue()));
            } catch (NumberFormatException ex) {
                resultField.setText("Valeur invalide");
            }
        });


        container.getChildren().addAll(inputField, currencyA, currencyB, calculButton);

        resultField = new TextField();
        resultField.setEditable(false);
        resultField.setPrefHeight(40.0);
        VBox.setMargin(resultField, new Insets(0, 10, 10, 10));

        Label switchToCurrencyLink = new Label("Afficher la calc standard");
        switchToCurrencyLink.setStyle("color: #0f86ff; -fx-cursor: pointer;");
        VBox.setMargin(switchToCurrencyLink, new Insets(0, 10, 0, 10));
        switchToCurrencyLink.setOnMouseClicked(e -> {
            standardCalculator();
            System.out.println("switch clicked");
        });

        main.getChildren().clear();
        main.getChildren().addAll(title, separator, container, resultField, switchToCurrencyLink);
    }

    public void handleCurrencyChangeCalc(double value, Conversion.Currency currencyA, Conversion.Currency currencyB) {
        Conversion conversion = new Conversion(value, currencyA, currencyB);
        SocketConnection<Conversion, Double> socketConnection = new SocketConnection<>(conversion);
        Thread socketConnectionThread = new Thread(socketConnection);
        socketConnectionThread.start();

        try {
            socketConnectionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Recieved result : " + socketConnection.result);

        resultField.setText(socketConnection.result.toString());
    }
}

