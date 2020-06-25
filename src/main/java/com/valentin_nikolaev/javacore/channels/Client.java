package com.valentin_nikolaev.javacore.channels;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {

    private int    clientNumber;
    private Thread thread;

    private String clientMessages[] = {"Hello world!", "Hello another users!",
            "I`m first time " + "here!", "How are you doing?",
            "What is this the server, how is it name?", "Joe, are you" + " here?"};


    private final int PORT = 5454;

    public Client(int clientNumber) {
        this.clientNumber = clientNumber;
        this.thread       = new Thread(this, "Client №" + clientNumber);
        this.thread.start();
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            try (Socket socket = new Socket("localHost", PORT);
                 BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());
                 BufferedOutputStream outputStream = new BufferedOutputStream(
                         socket.getOutputStream())) {
                String userMassage = clientMessages[random.nextInt(5)];
                System.out.println(
                        "Client №" + this.clientNumber + " send message: " + userMassage);
                outputStream.write(userMassage.getBytes());
                String serverMessage = new String(inputStream.readAllBytes());
                System.out.println(serverMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                this.thread.wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public Thread getThread() {
        return thread;
    }
}



