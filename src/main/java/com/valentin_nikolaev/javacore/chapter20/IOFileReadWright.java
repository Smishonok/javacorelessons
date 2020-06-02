package com.valentin_nikolaev.javacore.chapter20;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

public class IOFileReadWright {
    private String rootPath         = this.getClass().getResource("").getPath();
    private String projectSources   = rootPath + "IOResources";
    private String fileToReadPath   = projectSources + "/fileToRead.txt";
    private String fileToWrightPath = projectSources + "/fileToWright.txt";

    private File sourceDirectory;
    private File fileToRead;
    private File fileToWrit;

    private String testText = "This is the text testing of IO methods working. If this text " +
            "is not the same\nafter reading from the file, the app has errors and should be " +
            "checked.";


    public IOFileReadWright() {
        createSourceDirectory();
        isFileToReadExists();
        createFileToWright();
    }

    private void createSourceDirectory() {
        this.sourceDirectory = new File(this.projectSources);
        if (! this.sourceDirectory.exists()) {
            this.sourceDirectory.mkdir();
        }
    }

    private void isFileToReadExists() {
        this.fileToRead = new File(this.fileToReadPath);
        System.out.println("File for first reading exists: " + this.fileToRead.exists());
    }

    private void createFileToWright() {
        this.fileToWrit = new File(this.fileToWrightPath);
        try {
            this.fileToWrit.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getSourceDirectory() {
        return sourceDirectory;
    }

    public File getFileToRead() {
        return fileToRead;
    }

    public File getFileToWrit() {
        return fileToWrit;
    }

    public String getTestText() {
        return testText;
    }

    public String getTextFromFile(File file) {
        String textFromFile = "";
        char   buffer[]     = new char[8192];
        int    charactersRead;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file, Charset.forName("UTF-8")))) {
            while ((charactersRead = reader.read(buffer)) != - 1) {
                if (buffer.length > charactersRead) {
                    buffer = Arrays.copyOf(buffer, charactersRead);
                }
                textFromFile += new String(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textFromFile;
    }

    public void writeTextIntoFile(File file, String text) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, Charset.forName("UTF-8")))) {
            writer.write(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        boolean          isTestTextEq;
        IOFileReadWright testIO = new IOFileReadWright();

        isTestTextEq = testIO.getTestText().equals(testIO.getTextFromFile(testIO.getFileToRead()));
        System.out.println("\nTest text:\n" + testIO.getTestText());
        System.out.println("Text from file:\n" + testIO.getTextFromFile(testIO.getFileToRead()));
        System.out.println("Text from test file is equaled test text: " + isTestTextEq);

        testIO.writeTextIntoFile(testIO.getFileToWrit(), testIO.getTestText());
        isTestTextEq = testIO.getTestText().equals(
                testIO.getTextFromFile(testIO.getFileToWrit()));
        System.out.println("\nTest text:\n" + testIO.getTestText());
        System.out.println("Text from file:\n" + testIO.getTextFromFile(testIO.getFileToWrit()));
        System.out.println("Text from test file is equaled test text: " + isTestTextEq);


    }


}
