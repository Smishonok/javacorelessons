package com.valentin_nikolaev.javacore.chapter21;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathExamples {

    public static void main(String[] args) {
        try {
            Path projectRootPath = Path.of(ClassLoader.getSystemResource("").toURI());
            Path sourcesPath     = projectRootPath.resolve("NIOResources");
            if (! Files.exists(sourcesPath)) {
                Files.createDirectory(sourcesPath);
            }
            Path newDirectory = sourcesPath.resolve("txtFilesHolder");
            Path relativePath = projectRootPath.relativize(newDirectory);
            System.out.println(projectRootPath.toString());
            System.out.println(sourcesPath);
            System.out.println(newDirectory);
            System.out.println(relativePath);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }



}
