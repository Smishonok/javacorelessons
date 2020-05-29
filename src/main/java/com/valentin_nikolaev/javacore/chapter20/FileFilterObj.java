package com.valentin_nikolaev.javacore.chapter20;

import java.io.File;

public class FileFilterObj {
    private static String expr = "";

    static boolean getFilesContainsExpr(File directory, String fileName) {
        boolean fileContainsExpr = false;
        if (fileName.contains(expr)) {
            fileContainsExpr = true;
        }
        return fileContainsExpr;
    }

    public static void main(String[] args) {
        File directory = new File("src/main/resources");
        expr = "test";

        String[] namesInDirectory = directory.list(FileFilterObj::getFilesContainsExpr);
        for (String fileName : namesInDirectory) {
            System.out.println(fileName);
        }
    }
}
