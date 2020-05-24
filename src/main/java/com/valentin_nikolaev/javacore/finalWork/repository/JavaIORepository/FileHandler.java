package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;

public class FileHandler {

    static  Logger log = Logger.getLogger(FileHandler.class.getName());
    private File   handledFile;


    public FileHandler(File file) {
        this.handledFile = file;
    }

    public void addDataIntoFile(String data,boolean append) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(handledFile, Charset.forName("UTF-8"), append))) {
            writer.write(data);
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e);
        } catch (IOException e) {
            log.error("IO error: " + e);
        }
    }

    public void addDataIntoFile(String data) {
        this.addDataIntoFile(data,true);
    }

    public void removeDataById(long id) {
        String dataAfterRemoving = getDataExcludeId(id);
        this.addDataIntoFile(dataAfterRemoving,false);
    }

    private String getDataExcludeId(long id) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(handledFile, Charset.forName("UTF-8")))) {
            while (reader.read() > 0) {
                String dataLine = reader.readLine();
                if (!isDataLineConsistsId(id, dataLine)) {
                    data.append(dataLine);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e);
        } catch (IOException e) {
            log.error("IO error: " + e);
        }

        return data.toString();
    }

    private boolean isDataLineConsistsId(long id, String dataLine) {
        String[] dataArray = dataLine.split(",");
        long parsedId = Long.parseLong(dataArray[0]);
        return id == parsedId;
    }


}
