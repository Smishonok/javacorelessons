package com.valentin_nikolaev.javacore.channels;

import java.io.*;
import java.net.Socket;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
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
        try (Socket socket = new Socket("localHost", PORT);
             BufferedOutputStream outputStream = new BufferedOutputStream(
                     socket.getOutputStream())) {
            System.out.println("Status of socket connection: " + socket.isConnected());

            if (socket.isConnected()) {
                String clientMessage = clientMessages[random.nextInt(5)];
                System.out.println(
                        "Client №" + this.clientNumber + " send message: " + clientMessage);
                outputStream.write(clientMessage.getBytes());
                outputStream.flush();

            }

            Thread.sleep(random.nextInt(500));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    public Thread getThread() {
        return thread;
    }
}



