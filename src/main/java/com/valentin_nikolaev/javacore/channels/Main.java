package com.valentin_nikolaev.javacore.channels;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();

        Client client1 = new Client(1);

        client1.getThread().join();
        server.getThread().join();


        server.stop();
        System.out.println("Server is alive: "+server.getThread().isAlive());



    }


}
