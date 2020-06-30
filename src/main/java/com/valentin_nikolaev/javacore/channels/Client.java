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

    private          int               clientNumber;
    private          Thread            thread;
    private          Random            random;
    private          InetSocketAddress inetSocketAddress;
    private          SocketChannel     socketChannel;

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

        //disconnect();
    }

    private void sendMessage() {
        try {
            String     clientMessage = clientMessages[random.nextInt(5)];
            ByteBuffer byteBuffer    = ByteBuffer.wrap(clientMessage.getBytes());
            this.socketChannel.write(byteBuffer);
            Thread.sleep(random.nextInt(500));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage() {
        System.out.println("Client prepare to receive message from server.");
        StringBuilder serverMessage = new StringBuilder();
        ByteBuffer    buffer        = ByteBuffer.allocate(1024);
        try {
            System.out.println("Client try to read data from channel.");
            socketChannel.read(buffer);
            buffer.flip();
            byte byteBuffer[] = new byte[buffer.remaining()];
            buffer.get(byteBuffer, 0, buffer.limit());
            serverMessage.append(new String(byteBuffer));
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Read operation is ended.");
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
            e.printStackTrace();
        }

        return this;
    }
}



