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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            writer.write(this.prepareDataForSerialisation(region));
        } catch (IOException e) {
            log.error("Can`t write the region`s data into repository file: " + e.getMessage());
        }

    }

    @Override
    public Region get(Long id) {
        Optional<Region> region = Optional.empty();

        try {
            region = Files.lines(regionRepositoryPath).filter(
                    regionData->this.parseRegionId(regionData) == id).map(this::parseRegion)
                          .findFirst();
        } catch (IOException e) {
            log.error("Users`s repository file can`t be opened and read: " + e.getMessage());
        }

        if (! region.isEmpty()) {
            return region.get();
        } else {
            throw new IllegalArgumentException(
                    "Region with id " + id + "' does not contain in " + "database.");
        }
    }

    @Override
    public void remove(Long id) {
        List<String> regionsList = getRegionsListExcludeRegionWith(id);

        try (BufferedWriter writer = Files.newBufferedWriter(regionRepositoryPath,
                                                             Charset.forName("UTF-8"),
                                                             StandardOpenOption.WRITE)) {
            for (String region : regionsList) {
                writer.write(region);
            }
        } catch (IOException e) {
            log.error("Can`t write in repository file with regions data: " + e.getMessage());
        }
    }

    private List<String> getRegionsListExcludeRegionWith(long id) {
        List<String> regionsList = new ArrayList<>();
        try {
            regionsList = Files.lines(regionRepositoryPath).filter(
                    regionData->this.parseRegionId(regionData) != id).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with regions data: " + e.getMessage());
        }
        return regionsList;
    }

    @Override
    public List<Region> getAll() {
        return getRegionsListExcludeRegionWith(0).stream().map(this::parseRegion).collect(
                Collectors.toList());
    }

    @Override
    public void removeAll() {
        if (Files.exists(regionRepositoryPath)) {
            try {
                Files.delete(regionRepositoryPath);
            } catch (IOException e) {
                log.error("The repository file can`t be deleted: " + e.getMessage());
            }
        }

        try {
            Files.createFile(regionRepositoryPath);
        } catch (IOException e) {
            log.error("The repository file can`t be created: " + e.getMessage());
        }
    }

    @Override
    public boolean isExists(Long id) {
        boolean isExists = false;

        try {
            isExists = Files.lines(regionRepositoryPath).filter(
                    regionData->this.parseRegionId(regionData) == id).findFirst().isEmpty();
        } catch (IOException e) {
            log.error("Can`t read repository file with region data: " + e.getMessage());
        }
        return isExists;
    }

    private String prepareDataForSerialisation(Region region) {
        return "Region`s id:" + region.getId() + ";" + "Region`s name:" + region.getName() + ";\n";
    }

    private long parseRegionId(String regionData) {
        Scanner scanner = new Scanner(regionData);
        scanner.useDelimiter(";");

        scanner.findInLine("Region`s id:");
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

        scanner.findInLine("Region`s name:");
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

        long   id   = parseRegionId(regionData);
        String name = parseRegionName(regionData);

        return new Region(id, name);
    }

}
