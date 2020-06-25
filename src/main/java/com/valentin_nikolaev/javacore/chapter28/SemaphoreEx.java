package com.valentin_nikolaev.javacore.chapter28;

import java.util.concurrent.Semaphore;

class SharedResource {

    volatile private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class Incrementer implements Runnable {

    private Thread         thread;
    private Semaphore      semaphore;
    private SharedResource resource;

    public Incrementer(Semaphore semaphore, SharedResource resource) {
        this.semaphore = semaphore;
        this.resource  = resource;
        thread         = new Thread(this, this.getClass().getSimpleName());
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        System.out.println("Thread: " + this.getClass().getSimpleName() + " started.");
        try {
            System.out.println("Thread " + this.getClass().getSimpleName() +": try to get lock on" +
                                       " the recourse.");
            semaphore.acquire();
            System.out.println("Thread " + this.getClass().getSimpleName() +": get lock on the " +
                                       "resource.");

            for (int i = 5; i > 0; i--) {
                this.resource.setCount(this.resource.getCount() + 1);
                System.out.println("Thread " + this.getClass().getSimpleName() +": resource " +
                                           "value:" +
                        " " + this.resource.getCount());
            }

            System.out.println("Thread " + this.getClass().getSimpleName() +": release the " +
                                       "resource.");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Decrementer implements Runnable {

    private Thread         thread;
    private Semaphore      semaphore;
    private SharedResource resource;

    public Decrementer(Semaphore semaphore, SharedResource sharedResource) {
        this.semaphore = semaphore;
        this.resource  = sharedResource;
        this.thread    = new Thread(this, this.getClass().getSimpleName());
        this.thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        System.out.println("Thread: " + this.getClass().getSimpleName() + " started.");
        try {
            System.out.println("Thread " + this.getClass().getSimpleName() +": try to get lock on" +
                                       " the recourse.");
            semaphore.acquire();
            System.out.println("Thread " + this.getClass().getSimpleName() + ": get lock on the " +
                                       "resource.");

            for (int i = 5; i > 0; i--) {
                this.resource.setCount(this.resource.getCount() - 1);
                System.out.println(
                        "Thread " + this.getClass().getSimpleName() + ": resource value: " +
                                this.resource.getCount());
            }

            System.out.println("Thread " + this.getClass().getSimpleName() +": release the " +
                                       "resource.");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SemaphoreEx {

    public static void main(String[] args) throws InterruptedException {
        Semaphore      semaphore   = new Semaphore(1);
        SharedResource resource    = new SharedResource();
        Incrementer    incrementer = new Incrementer(semaphore, resource);
        Decrementer    decrementer = new Decrementer(semaphore, resource);
        incrementer.getThread().join();
        decrementer.getThread().join();
    }


}
