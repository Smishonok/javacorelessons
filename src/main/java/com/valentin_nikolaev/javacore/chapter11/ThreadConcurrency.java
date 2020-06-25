package com.valentin_nikolaev.javacore.chapter11;

import java.time.format.DateTimeFormatter;

class Producer implements Runnable {

    private DataTransferee dataTransferee;
    private int messageCounter = 0;
    private Thread thread;

    public Producer(DataTransferee dataTransferee) {
        this.dataTransferee = dataTransferee;
        thread              = new Thread(this, "Producer");
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        boolean loop = true;
        while (loop) {
            String message = "Message №" + (++ messageCounter);
            System.out.println(thread.getName()+": "+message);
            this.dataTransferee.sendMessage(message);
        }
    }
}

class Consumer implements Runnable {

    private DataTransferee dataTransferee;
    private Thread thread;

    public Consumer(DataTransferee dataTransferee) {
        this.dataTransferee = dataTransferee;
        thread = new Thread(this, "Consumer");
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        boolean loop = true;
        while (loop) {
            System.out.println(thread.getName()+" receive message: "+this.dataTransferee.getMessage());
        }
    }
}

class DataTransferee {

    private String  message      = "";
    private boolean isBufferFull = false;

    synchronized public String getMessage() {
        if (! isBufferFull) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isBufferFull = false;
        notify();
        return message;
    }

    synchronized public void sendMessage(String message) {
        if (isBufferFull) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isBufferFull = true;
        this.message = message;
        notify();
    }
}


public class ThreadConcurrency {

    public static void main(String[] args) throws InterruptedException {
        DataTransferee dataTransferee = new DataTransferee();
        Producer producer = new Producer(dataTransferee);
        Consumer consumer = new Consumer(dataTransferee);
        producer.getThread().join();
        consumer.getThread().join();
    }


}
