package com.valentin_nikolaev.javacore.chapter13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BufferedReaderListing2 {
    public static void main(String[] args) {
        List<String> text = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the words. The word \"stop\" will stop the app");
            String str = "";
            do {
                str = reader.readLine();
                text.add(str);
            } while (!str.equals("stop"));

        } catch (IOException e) {
            System.out.println(e);
        }

        for (String str : text) {
            System.out.println(str);
        }
    }

}
