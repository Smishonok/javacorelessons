package com.valentin_nikolaev.javacore.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server implements Runnable {

    private          Thread              thread;
    private          ByteBuffer          sharedBuffer;
    private          ServerSocket        serverSocket;
    private          ServerSocketChannel serverSocketChannel;
    private          InetSocketAddress   serverInetAddress;
    private          Selector            selector;
    private volatile boolean             isServerAlive = true;

    private final int SERVER_PORT = 8989;
    private final int BUFFER_SIZE = 1024;

    public Server() {
        setUpServerEnvironment();
        this.thread = new Thread(this, "Server");
        this.thread.setDaemon(true);
        this.thread.start();
    }

    @Override
    public void run() {
        while (isServerAlive) {

        }

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

    public Server stop() {
        this.isServerAlive = false;
        return this;
    }

    private void clo


}
