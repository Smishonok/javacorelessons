package com.valentin_nikolaev.javacore.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable {

    private          Thread       thread;
    private          ByteBuffer   sharedBuffer;
    private          Selector     selector;
    private          ServerSocket serverSocket;
    private          boolean      written       = true;
    private          int          serverResponseNumber;
    private volatile boolean      isServerAlive = true;

    private final int SERVER_PORT = 5454;
    private final int BUFFER_SIZE = 1024;

    public Server() {
        setUpServerEnvironment();
        this.serverResponseNumber = 0;
        this.thread               = new Thread(this, "Server");
        this.thread.start();
    }

    private void setUpServerEnvironment() {
        this.sharedBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            this.serverSocket = serverSocketChannel.socket();
            InetSocketAddress serverInetAddress = new InetSocketAddress(SERVER_PORT);
            serverSocket.bind(serverInetAddress);
            this.selector = Selector.open();
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.err.println("Unable to setup environment.");
            System.exit(- 1);
        }
        System.out.println("Server parameters was setup.");
    }

    @Override
    public void run() {
        System.out.println("Server is started.");
        while (isServerAlive) {
            try {
                int keysCount = this.selector.select(3000);
                if (keysCount != 0) {
                    Set<SelectionKey> keySet = this.selector.selectedKeys();
                    handleKeySet(keySet);
                }
            } catch (IOException e) {
                System.err.println("Error during select: " + e.getMessage());
                try {
                    this.selector.close();
                    this.serverSocket.close();
                } catch (IOException ex) {
                    System.err.println(
                            "Server selector and socket was not closed: " + ex.getMessage());
                }
            }

            if (! isServerAlive) {
                try {
                    this.selector.close();
                    this.serverSocket.close();

                    System.out.println("Server is stopped.");
                } catch (IOException e) {
                    System.err.println(
                            "Server was not stopped, selector and serverSocket was not closed:" + " " +
                                    e.getMessage());
                }
            }
        }
    }

    private void handleKeySet(Set<SelectionKey> keySet) {
        Iterator<SelectionKey> keyIterator = keySet.iterator();

        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            handleKey(key);
            keyIterator.remove();
        }
    }

    private void handleKey(SelectionKey key) {
        if (key.isValid()) {
            acceptClient(key);
            readDataFromChannel(key);
            sendRequestToClient(key);
        } else {
            System.err.println("Invalid key selected.");
            key.cancel();
        }
    }

    private void acceptClient(SelectionKey key) {
        if (key.isAcceptable()) {
            try {
                ServerSocketChannel socketChannel          = (ServerSocketChannel) key.channel();
                SocketChannel       connectedSocketChannel = socketChannel.accept();
                connectedSocketChannel.configureBlocking(false);
                connectedSocketChannel.register(this.selector,
                                                SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                System.out.println("New user added to the conversation!");
            } catch (IOException e) {
                System.err.println("Socket was not accepted: " + e.getMessage());
                key.cancel();
            }
        }
    }

    private void readDataFromChannel(SelectionKey key) {
        if (key.isReadable() && written) {
            SocketChannel client = (SocketChannel) key.channel();

            StringBuilder clientData = new StringBuilder();
            try {
                while (client.read(this.sharedBuffer) > 0) {
                    this.sharedBuffer.flip();
                    byte byteBuffer[] = new byte[this.sharedBuffer.remaining()];
                    this.sharedBuffer.get(byteBuffer, 0, this.sharedBuffer.limit());
                    clientData.append(new String(byteBuffer));
                    this.sharedBuffer.clear();
                }
            } catch (IOException e) {
                System.err.println("Message was not read from channel: " + e.getMessage());
                key.cancel();
            }

            if (! clientData.equals("END")) {
                key.attach(clientData.toString());
                this.written = false;
            } else {
                closeSocketChannel(key);
            }
        }
    }

    private void sendRequestToClient(SelectionKey key) {
        if (key.isValid() && key.isWritable() && ! written) {
            SocketChannel client = (SocketChannel) key.channel();
            written = true;
            try {
                String userMessage = (String) key.attachment();
                String serverResponse =
                        ++ this.serverResponseNumber + " Server response: Client message: \"" +
                                userMessage + "\" received.";
                byte responseInBytes[] = serverResponse.getBytes();
                this.sharedBuffer = ByteBuffer.wrap(responseInBytes);
                client.write(this.sharedBuffer);
                this.sharedBuffer.clear();
            } catch (IOException e) {
                System.err.println("Unable to write in chanel: " + e.getMessage());
                key.cancel();
            }
        }
    }

    private void closeSocketChannel(SelectionKey key) {
        try {
            key.channel().close();
            key.cancel();
        } catch (IOException e) {
            System.err.println("Socket was not closed: " + e.getMessage());
        }
    }

    public Thread getThread() {
        return thread;
    }

    public Server stop() {
        System.out.println("Start server stopping procedure.");
        this.isServerAlive = false;
        this.selector.wakeup();
        return this;
    }
}
