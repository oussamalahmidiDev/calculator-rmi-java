package com.learn;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class SocketConnection<T, R> implements Runnable {

    T operation;
    volatile R result;

    public SocketConnection(T operation) {
        this.operation = operation;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("0.0.0.0", 3001);
            System.out.println("Connected to the socket");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Writing object.");
            objectOutputStream.writeObject(operation);
            objectOutputStream.reset();
            System.out.println("Object sent.");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = (R) objectInputStream.readObject();
            System.out.println("Received: " + result);

            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
