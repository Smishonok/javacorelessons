package com.valentin_nikolaev.javacore.channels;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();

        Client client1 = new Client(1);
        Client client2 = new Client(2);

        client1.getThread().join();
        client2.getThread().join();


        Thread.sleep(1000);
        server.stop();


    }


}
