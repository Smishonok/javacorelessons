package com.valentin_nikolaev.javacore.channels;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();

        Client client1 = new Client(1);
        Client client2 = new Client(2);
        Client client3 = new Client(3);
        Client client4 = new Client(4);
        Client client5 = new Client(5);
        Client client6 = new Client(6);

        client1.getThread().join();
        client2.getThread().join();
        client3.getThread().join();
        client4.getThread().join();
        client5.getThread().join();
        client6.getThread().join();

        server.stop();
    }
}
