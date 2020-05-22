package com.valentin_nikolaev.javacore.chapter13;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

public class ShowFileListing {
    static Logger log = Logger.getLogger(ShowFileListing.class.getName());


    public static void main(String[] args) {
        File testFile = new File("src/main/resources/testFile.txt");

        BufferedInputStream reader       = null;
        String              textFromFile = "";

        try {
            log.debug("Try to read data from file: \"" + testFile + "\"");
            reader = new BufferedInputStream(new FileInputStream(testFile));
            byte[] bufferBytes = new byte[8192];

            while (reader.available() > 0) {

                log.debug("Check the buffer size.");
                if (bufferBytes.length > reader.available()) {
                    bufferBytes = Arrays.copyOf(bufferBytes, reader.available());
                    log.debug("Buffer was resized.");
                }

                reader.read(bufferBytes);
                textFromFile += new String(bufferBytes, Charset.forName("UTF-8"));
            }

        } catch (IOException e) {
            log.error(e);
        } finally {
            if (reader != null) {
                try {
                    log.debug("Starting the closing file procedure.");
                    reader.close();
                } catch (IOException e) {
                    log.error("Closing file error: " + e);
                }
            }
        }

        System.out.println(textFromFile);
    }
}
