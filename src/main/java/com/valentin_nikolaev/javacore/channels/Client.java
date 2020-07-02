package com.valentin_nikolaev.javacore.channels;

import java.awt.desktop.SystemEventListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Client implements Runnable {

    private int               clientNumber;
    private Thread            thread;
    private Random            random;
    private InetSocketAddress inetSocketAddress;
    private SocketChannel     socketChannel;

    private final int PORT = 5454;


    private String clientMessages[] = {"Hello world!", "Hello another users!",
            "I`m first time " + "here!", "How are you doing?",
            "What is this the server, how is it name?", "Joe, are you" + " here?"};


    public Client(int clientNumber) {
        this.clientNumber = clientNumber;
        this.random       = new Random();
        this.thread       = new Thread(this, "Client №" + clientNumber);
        this.thread.start();
    }

    @Override
    public void run() {
        initiateSocketChannel();
        for (int i = 0; i < 6; i++) {
            sendMessage();
            receiveMessage();
        }
        sendMessage("END");

        //disconnect();
    }

    private void sendMessage() {
        String clientMessage =
                "Client №" + this.clientNumber + ": " + clientMessages[random.nextInt(5)];
        sendMessage(clientMessage);
    }

    private void sendMessage(String message) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
            Thread.sleep(random.nextInt(1000));
            this.socketChannel.write(byteBuffer);
        } catch (IOException | InterruptedException e) {
            System.err.println("Message was not send to server: " + e.getMessage());
        }
    }

    private void receiveMessage() {
        StringBuilder serverMessage = new StringBuilder();
        ByteBuffer    buffer        = ByteBuffer.allocate(1024);
        try {
            socketChannel.read(buffer);
            buffer.flip();
            byte byteBuffer[] = new byte[buffer.remaining()];
            buffer.get(byteBuffer, 0, buffer.limit());
            serverMessage.append(new String(byteBuffer));
            buffer.clear();
        } catch (IOException e) {
            System.err.println("Message was not received from server: " + e.getMessage());
        }
        System.out.println(serverMessage.toString());
    }

    private void initiateSocketChannel() {
        try {
            this.inetSocketAddress = new InetSocketAddress("localHost", PORT);
            this.socketChannel     = SocketChannel.open();
            this.socketChannel.connect(inetSocketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Thread getThread() {
        return thread;
    }

    public Client disconnect() {
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            System.err.println("Client`s socket was not closed: " + e.getMessage());
        }
        return this;
    }
}



