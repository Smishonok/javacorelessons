package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class JavaIORegionRepositoryImpl implements RegionRepository {

    static Logger log = Logger.getLogger(JavaIORegionRepositoryImpl.class);

    private Path regionRepositoryPath;

    public JavaIORegionRepositoryImpl(Path repositoryRootPath) {
        this.regionRepositoryPath = repositoryRootPath.resolve("regionRepository.txt");
        createRegionRepository();
    }

    private void createRegionRepository() {
        log.debug("Checking is repository file with region data exists.");
        if (! Files.exists(this.regionRepositoryPath)) {
            log.debug(
                    "Region repository does not exist, started the creation of a repository file.");
            try {
                Files.createFile(this.regionRepositoryPath);
            } catch (IOException e) {
                log.debug("File \"regionRepository.txt\" can`t be created: " + e.getMessage());
            }
            log.debug("The repository file with regions data created successfully");
        } else {
            log.debug("The repository file with regions data exists.");
        }
    }

    @Override
    public void add(Region region) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(regionRepositoryPath,
                                                            Charset.forName("UTF-8"),
                                                            StandardOpenOption.WRITE,
                                                            StandardOpenOption.APPEND);
            writer.write(this.getDataForSerialisation(region));
        } catch (IOException e) {
            log.error("Can`t write the region`s data into repository file: " + e.getMessage());
        }

    }

    @Override
    public Region get(Long id) {


    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<Region> getAll() {

    }

    @Override
    public void removeAll() {

    }

    private String getDataForSerialisation(Region region) {
        return "Region id:" + region.getId() + ";" + "Region name:" + region.getName() + ";";
    }

    private long parseRegionId(String regionData) {
        Scanner scanner = new Scanner(regionData);
        scanner.useDelimiter(";");

        scanner.findInLine("Region id:");
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the user Id.");
        }
    }

    private String parseRegionName(String regionData) {
        Scanner scanner = new Scanner(regionData);
        scanner.useDelimiter(";");

        scanner.findInLine("Region name:");
        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the user Id.");
        }
    }

    private Region parseRegion(String regionData) {
        if (regionData.isBlank() || regionData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        long       id = parseRegionId(regionData);
        String     name = parseRegionName(regionData);

        return new Region(id, name);
    }

}
