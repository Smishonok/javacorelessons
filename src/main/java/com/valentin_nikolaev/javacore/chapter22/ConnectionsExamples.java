package com.valentin_nikolaev.javacore.chapter22;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConnectionsExamples {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.fuzzwork.co.uk/api/typeid.php?typename=Tritanium");
        URLConnection connection = url.openConnection();
        try (BufferedInputStream inputStream = new BufferedInputStream(
                connection.getInputStream())) {
            byte buffer[] = inputStream.readAllBytes();
            System.out.println(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, List<String>> pageHeaderFieldsValues = connection.getHeaderFields();
        Set<String> headerFields = pageHeaderFieldsValues.keySet();
        StringBuilder headerView = new StringBuilder();
        for (String field : headerFields) {
            headerView.append(field).append(":\n");
            List<String> fieldsValues = pageHeaderFieldsValues.get(field);
            fieldsValues.forEach(value->headerView.append("\t").append(value).append(";\n"));
        }
        System.out.println(headerView);


        long requestStart = System.currentTimeMillis();
        long serverResponse = connection.getDate();
        long requestEnd = System.currentTimeMillis();

        System.out.println("Server response time: "+(serverResponse-requestStart));
        System.out.println("Sum request time: "+(requestEnd-requestStart));



    }


}
