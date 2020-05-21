package com.valentin_nikolaev.javacore.chapter13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedReaderListing {
    public static void main(String[] args) {
        char c;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter letters. Letter 'q' will end the app");
            do {
                c = (char) reader.read();
                System.out.print(c);
            } while (c != 'q');
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
