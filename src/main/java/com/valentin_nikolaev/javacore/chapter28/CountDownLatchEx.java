package com.valentin_nikolaev.javacore.chapter28;


import java.io.InputStream;
import java.io.Writer;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

class SportCar implements Runnable {

    private static int lastCarNumber = 0;
    private int carNumber;

    {
        carNumber = ++ lastCarNumber;
    }


    private Thread thread;
    private CountDownLatch countDownLatch;

    public SportCar(CountDownLatch countDownLatch) {
        this.thread = new Thread(this, "Car №" + carNumber);
        this.countDownLatch = countDownLatch;
        this.thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        try {
            System.out.println("Car №"+carNumber+" prepared to the race.");
            this.countDownLatch.countDown();
            Random random = new Random();
            Thread.sleep(random.nextInt(10)*1000);
            System.out.println("Car №"+carNumber+" finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class CountDownLatchEx {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 5; i > 0; i--) {
            Thread.sleep(1000);
            new SportCar(countDownLatch);
        }
        countDownLatch.await();
        System.out.println("Race is started!");

        System.out.println("Race is ended!");

    }

}
