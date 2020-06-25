package com.valentin_nikolaev.javacore.chapter22;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CSVLoader {


    static class GameEntity {
        long   id;
        String name;

        public GameEntity(long id, String name) {
            this.id   = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "GameEntity{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

        URL url = new URL("https://www.fuzzwork.co.uk/resources/typeids.csv");
        Path types = Path.of("types.txt");


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String csv = "";
        if (Files.exists(types) && Files.size(types) > 100) {
            csv = Files.lines(types).collect(StringBuilder::new, StringBuilder::append,
                                       StringBuilder::append).toString();
        } else {
            try (BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
                 BufferedWriter writer = Files.newBufferedWriter(types)) {
                csv = new String(inputStream.readAllBytes()).replace("\n", ",");
                writer.write(csv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        float fileSize = (float) Files.size(types)/1000_000;

        System.out.println(String.format("%.2f",fileSize));

        Map<String, GameEntity> entities = new HashMap<>();

        Scanner scanner = new Scanner(csv);
        scanner.useDelimiter(",");

        for (int i = 0; i < 5; i++) {
            long   id   = Long.parseLong(scanner.next().replace("\"", ""));
            String name = scanner.next();
            scanner.next();
            entities.put(Long.valueOf(id).toString(), new GameEntity(id, name));
        }


        System.out.println(entities.get("5"));


    }


}
