package com.valentin_nikolaev.javacore.channels;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {

    private int                  clientNumber;
    private Thread               thread;
    private Socket               socket;
    private BufferedInputStream  inputStream;
    private BufferedOutputStream outputStream;

    private String clientMessages[] = {"Hello world!", "Hello another users!",
            "I`m first time " + "here!", "How are you doing?",
            "What is this the server, how is it name?", "Joe, are you" + " here?"};


    private final int PORT = 5454;

    public Client(int clientNumber) {
        this.clientNumber = clientNumber;
        initiateClient();
        this.thread = new Thread(this, "Client №" + clientNumber);
        this.thread.start();
    }

    private void initiateClient() {
        try {
            this.socket       = new Socket("localHost", PORT);
            this.inputStream  = new BufferedInputStream(this.socket.getInputStream());
            this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Unable to initiate client.");
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            try {
                this.socket       = new Socket("localHost", PORT);
                this.inputStream  = new BufferedInputStream(this.socket.getInputStream());
                this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            String userMassage = clientMessages[random.nextInt(5)];
            System.out.println("Client №"+this.clientNumber+" send message: "+userMassage);
            try {
                outputStream.write(userMassage.getBytes());
            } catch (IOException e) {
                System.err.println("Unable to write message in channel.");
            }
            try {
                String serverMessage = new String(inputStream.readAllBytes());
                System.out.println(serverMessage);
            } catch (IOException e) {
                System.err.println("Unable to reade data from channel.");
            }

            try {
                this.thread.wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                this.inputStream.close();
                this.outputStream.close();
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Thread getThread() {
        return thread;
    }
}



