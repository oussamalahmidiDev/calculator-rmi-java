package com.learn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static Operation receivedOperation;
    private static Conversion receivedConversion;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// write your code here
        System.out.println("Test logic");

        ServerSocket serverSocket = new ServerSocket(3001);
        while(true) {
            Socket connection = serverSocket.accept();

            ObjectInputStream objectInputStream = new ObjectInputStream(connection.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(connection.getOutputStream());

            objectOutputStream.reset();
            Object receivedObject = objectInputStream.readObject();
            if (receivedObject instanceof Operation) {
                receivedOperation = (Operation) receivedObject;
                System.out.println("Received operation : " + receivedOperation);
                objectOutputStream.writeObject(OperationService.calculate(receivedOperation));
                System.out.println("Sent result : " + OperationService.calculate(receivedOperation));
            }

            else if (receivedObject instanceof Conversion) {
                receivedConversion = (Conversion) receivedObject;
                System.out.println("Received conversion : " + receivedConversion);
                objectOutputStream.writeObject(ConversionService.convert(receivedConversion));
            }
        }
    }
}
