package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    static  Logger log = Logger.getLogger(FileHandler.class.getName());
    private File   handledFile;


    public FileHandler(File file) {
        this.handledFile = file;
    }

    public void addDataIntoFile(String data, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(handledFile, Charset.forName("UTF-8"), append))) {
            writer.write(data);
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e);
        } catch (IOException e) {
            log.error("IO file error: " + e);
        }
    }

    public void addDataIntoFile(String data) {
        this.addDataIntoFile(data, true);
    }

    public String getDataById(long id) {
        String dataLine = "";

        boolean firstMathFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(handledFile))) {
            while (! firstMathFound && reader.read() > 0) {
                dataLine = reader.readLine();
                if (isDataLineConsistsId(id, dataLine)) {
                    firstMathFound = true;
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e);
        } catch (IOException e) {
            log.error("IO file error: " + e);
        }

        return dataLine;
    }

    public void removeDataById(long id) {
        String dataAfterRemoving = getDataExcludeId(id);
        this.addDataIntoFile(dataAfterRemoving, false);
    }

    public List<String> getAllDataLines() {
        List<String> dataLinesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(handledFile))) {
            while (reader.read() > 0) {
                String dataLine = reader.readLine();
                dataLinesList.add(dataLine);
            }
        } catch (FileNotFoundException e) {
            log.error("File not found: " + e);
        } catch (IOException e) {
            log.error("IO file error: " + e);
        }
        return dataLinesList;
    }

    public void removeAll() {
        handledFile.delete();
        handledFile.mkdir();
    }

    private String getDataExcludeId(long id) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(handledFile, Charset.forName("UTF-8")))) {
            while (reader.read() > 0) {
                String dataLine = reader.readLine();
                if (! isDataLineConsistsId(id, dataLine)) {
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
        long     parsedId  = Long.parseLong(dataArray[0]);
        return id == parsedId;
    }


}
