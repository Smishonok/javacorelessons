package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaIORegionRepositoryImpl implements RegionRepository {
    //TODO make loading repository file`s path from config property file
    private File        regionRepository = new File(
            "src/main/resources/FileRepository/regionRepository.txt");
    private FileHandler fileHandler;

    public JavaIORegionRepositoryImpl() {
        fileHandler = new FileHandler(regionRepository);
    }

    @Override
    public void add(Region region) {
        String regionData = region.getId() + "," + region.getName() + "\n";
        fileHandler.addDataIntoFile(regionData);
    }

    @Override
    public Region get(Long id) {
        String dataLine = fileHandler.getDataById(id);
        return parseDataLine(dataLine);
    }

    @Override
    public void remove(Long id) {
        fileHandler.removeDataById(id);
    }

    @Override
    public List<Region> getAll() {
        List<Region> regions   = new ArrayList<>();
        List<String> dataLines = fileHandler.getAllDataLines();

        for (String dataLine : dataLines) {
            regions.add(parseDataLine(dataLine));
        }

        return regions;
    }

    @Override
    public void removeAll() {
        fileHandler.removeAll();
    }

    private Region parseDataLine(String dataLine) throws IllegalArgumentException {
        if (dataLine == null || dataLine.isBlank() || dataLine.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String[] dataArray  = dataLine.split(",");
        long     id         = Long.parseLong(dataArray[0]);
        String   regionName = dataArray[1];
        Region   region     = new Region(id, regionName);
        return region;
    }
}
