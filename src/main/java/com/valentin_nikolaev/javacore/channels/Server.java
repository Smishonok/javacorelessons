package com.valentin_nikolaev.javacore.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable {

    private          Thread              thread;
    private          ByteBuffer          sharedBuffer;
    private          ServerSocket        serverSocket;
    private          ServerSocketChannel serverSocketChannel;
    private          InetSocketAddress   serverInetAddress;
    private          Selector            selector;
    private          String              clientMessage;
    private          int                 serverResponseNumber;
    private volatile boolean             isServerAlive = true;

    private final int SERVER_PORT = 5454;
    private final int BUFFER_SIZE = 1024;

    public Server() {
        setUpServerEnvironment();
        this.serverResponseNumber = 0;
        this.thread               = new Thread(this, "Server");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    private void setUpServerEnvironment() {
        this.sharedBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocket      = serverSocketChannel.socket();
            this.serverInetAddress = new InetSocketAddress(SERVER_PORT);
            this.serverSocket.bind(serverInetAddress);
            this.selector = Selector.open();
            this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.err.println("Unable to setup environment.");
            System.exit(- 1);
        }
        System.out.println("Server parameters was setup.");
    }

    @Override
    public void run() {
        while (isServerAlive) {
            System.out.println("Start to check selectable items.");
            try {
                int keysCount = this.selector.select();
                if (keysCount != 0) {
                    System.out.println(keysCount+" keys selected, start to process request.");
                    Set<SelectionKey> keySet = this.selector.selectedKeys();
                    processKeySet(keySet);
                }
            } catch (IOException e) {
                System.err.println("Error during select.");
                e.printStackTrace();
            }
        }
    }

    private void processKeySet(Set<SelectionKey> keySet) {
        System.out.println("Process new key set.");
        Iterator<SelectionKey> keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            System.out.println("Get new key from set.");
            SelectionKey key = keyIterator.next();
            keyIterator.remove();

            acceptClient(key);
            readDataFromChannel(key);
            sendRequestToClient(key);
        }
    }

    private void acceptClient(SelectionKey key) {
        if (key.isAcceptable()) {
            try {
                Socket        connectedSocket        = this.serverSocket.accept();
                SocketChannel connectedSocketChannel = connectedSocket.getChannel();

                if (connectedSocketChannel != null) {
                    connectedSocketChannel.configureBlocking(false);
                    connectedSocketChannel.register(this.selector, SelectionKey.OP_READ);
                    System.out.println("Accepted!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readDataFromChannel(SelectionKey key) {
        if (key.isReadable()) {
            System.out.println("Start to read.");
            SocketChannel client = (SocketChannel) key.channel();
            try {
                Socket socketToWrite = this.serverSocket.accept();
                SocketChannel channelToWrite = socketToWrite.getChannel();

                if (channelToWrite != null) {
                    channelToWrite.configureBlocking(false);
                    channelToWrite.register(this.selector, SelectionKey.OP_WRITE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Start to read from channel.");
                StringBuilder clientData = new StringBuilder();
                while (client.read(this.sharedBuffer) != - 1) {
                    this.sharedBuffer.flip();
                    clientData.append(new String(
                            Arrays.copyOf(this.sharedBuffer.array(), this.sharedBuffer.limit())));
                    this.sharedBuffer.clear();
                }
                this.clientMessage = clientData.toString();
                System.out.println(this.clientMessage);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequestToClient(SelectionKey key) {
        if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            String serverResponse =
                    this.serverResponseNumber + " Server response: \"Client message :" +
                            this.clientMessage + "received.\"";
            byte responseInBytes[] = serverResponse.getBytes();
            this.sharedBuffer = ByteBuffer.wrap(responseInBytes);
            try {
                client.write(this.sharedBuffer);
            } catch (IOException e) {
                System.err.println("Unable to write in chanel.");
            }
        }
    }

    public Server stop() {
        this.isServerAlive = false;
        return this;
    }
}
