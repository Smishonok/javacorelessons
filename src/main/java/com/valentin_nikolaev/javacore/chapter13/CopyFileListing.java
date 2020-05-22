package com.valentin_nikolaev.javacore.chapter13;

import java.io.*;
import java.util.Arrays;

public class CopyFileListing {

    public static void main(String[] args) {
        BufferedInputStream fis = null;
        BufferedOutputStream fos = null;

        File source = new File("src/main/resources/testFile.txt");
        File target = new File("src/main/resources/testFile2.txt");
        byte[] dataBuffer = new byte[8192];

        try {
            fis = new BufferedInputStream(new FileInputStream(source));
            fos = new BufferedOutputStream(new FileOutputStream(target));

            while (fis.available() > 0) {

                if (dataBuffer.length > fis.available()) {
                    dataBuffer = Arrays.copyOf(dataBuffer, fis.available());
                }

                fis.read(dataBuffer);
                fos.write(dataBuffer);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
