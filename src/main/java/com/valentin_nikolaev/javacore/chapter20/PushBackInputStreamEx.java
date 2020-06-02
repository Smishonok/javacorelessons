package com.valentin_nikolaev.javacore.chapter20;

import java.io.*;

public class PushBackInputStreamEx {

    public static void main(String[] args) {

        String               s        = "if (a == 4) a = 0;\n";
        byte                 buffer[] = s.getBytes();
        ByteArrayInputStream in       = new ByteArrayInputStream(buffer);

        int c;
        try (PushbackInputStream pbis = new PushbackInputStream(in)) {
            while ((c = pbis.read()) != - 1) {
                switch (c) {
                    case '=':
                        if ((c = pbis.read()) == '=') {
                            System.out.print(".eq.");
                        } else {
                            System.out.print("<-");
                            pbis.unread(c);
                        }
                        break;
                    default:
                        System.out.print((char) c);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
