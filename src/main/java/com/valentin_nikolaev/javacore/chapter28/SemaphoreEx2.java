package com.valentin_nikolaev.javacore.chapter28;

import java.util.concurrent.Semaphore;

class TransferObject {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}


class Consumer implements Runnable {

    private TransferObject sharedObject;
    private Semaphore      consumerSemaphore;
    private Semaphore      producerSemaphore;
    private Thread         thread;

    public Consumer(Semaphore consumerSemaphore, Semaphore producerSemaphore,
                    TransferObject sharedObject) {
        this.sharedObject      = sharedObject;
        this.consumerSemaphore = consumerSemaphore;
        this.producerSemaphore = producerSemaphore;
        this.thread            = new Thread(this, "Consumer");
        this.thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumer try to get lock on the resource.");
                this.consumerSemaphore.acquire();
                System.out.println("Consumer get the lock on the resource.");
                Thread.sleep(1000);
                System.out.println("Consumer receive message: " + this.sharedObject.getData());
                System.out.println("Consumer unlock the resource.");
                System.out.println("Number of permits available in consumer semaphore: "+this.consumerSemaphore.availablePermits());
                this.producerSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer implements Runnable {

    private Semaphore      consumerSemaphore;
    private Semaphore producerSemaphore;
    private Thread         thread;
    private TransferObject resource;
    private int            messageNumber;

    public Producer(Semaphore consumerSemaphore,Semaphore producerSemaphore,  TransferObject resource) {
        this.consumerSemaphore = consumerSemaphore;
        this.producerSemaphore = producerSemaphore;
        this.resource          = resource;
        this.messageNumber     = 0;
        this.thread            = new Thread(this, "Producer");
        this.thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Producer try to get a lock of the resource.");
                System.out.println("Producer get the lock of the resource.");
                this.producerSemaphore.acquire();
                Thread.sleep(1000);
                this.resource.setData("Producer send message №" + (++ messageNumber));
                System.out.println("Producer unlock the consumer semaphore.");
                this.consumerSemaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class SemaphoreEx2 {
    public static void main(String[] args) throws InterruptedException {
        Semaphore      consumerSemaphore = new Semaphore(0);
        Semaphore producerSemaphore = new Semaphore(1);
        TransferObject transferObject    = new TransferObject();
        Consumer       consumer          = new Consumer(consumerSemaphore,producerSemaphore, transferObject);
        Producer       producer          = new Producer(consumerSemaphore,producerSemaphore, transferObject);
        consumer.getThread().join();
        producer.getThread().join();
    }
}
