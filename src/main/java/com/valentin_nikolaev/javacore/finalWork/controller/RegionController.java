package com.valentin_nikolaev.javacore.finalWork.controller;

import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionController {

    static Logger log = Logger.getLogger(RegionController.class.getName());

    private RegionRepository    regionRepository;
    private Map<String, Region> regionsMapWithKeyNames;
    private Map<Long, Region>   regionMapWithKeyIds;

    public RegionController() throws ClassNotFoundException {
        initRegionRepository();
    }

    private void initRegionRepository() throws ClassNotFoundException {
        log.debug("Starting initialisation of Region repository");
        regionRepository = RepositoryManager.getRepositoryFactory().getRegionRepository();
        log.debug("User repository implementation is: " + regionRepository.getClass().getName());
    }

    public void addRegion(String name) {
        log.debug("Adding new region into repository.");
        Region region = new Region(name);
        regionRepository.add(region);
        log.debug("Adding the new region into the repository ended successfully.");
    }

    public void removeRegionWhitName(String name) {
        log.debug("Removing the region with name '" + name + "' from repository.");
        if (isRepositoryContainsRegionWithName(name)) {
            long removingRegionId = regionsMapWithKeyNames.get(name).getId();
            regionRepository.remove(removingRegionId);
            log.debug("Removing operation is ended.");
        }
        log.debug("The region with name '" + name + "' isn`t exists in repository. Operation is " +
                          "canceled.");
    }

    public void removeRegionWithId(Long id) {
        log.debug("Removing the region with name '" + id + "' from repository.");
        regionRepository.remove(id);
        log.debug("Removing operation is ended.");
    }

    public void removeAllRegions() {
        log.debug("Removing all regions from repository.");
        regionRepository.removeAll();
    }

    public Region getRegionByName(String name) throws IllegalArgumentException {
        log.debug("Getting the region with the name '" + name + "' from repository.");
        if (isRepositoryContainsRegionWithName(name)) {
            Region targetRegion = regionsMapWithKeyNames.get(name);
            return targetRegion;
        } else {
            log.error("Illegal region name. The region with name '" + name + "' isn`t contains in" +
                              "repository. Before calling this method need to check is repository" +
                              " contains the region with the calling name.");
            throw new IllegalArgumentException();
        }
    }

    public Region getRegionById(Long id) throws IllegalArgumentException {
        log.debug("Getting the region with the id '" + id + "' from repository.");
        if (isRepositoryContainsRegionWithId(id)) {
            Region targetRegion = regionMapWithKeyIds.get(id);
            return targetRegion;
        } else {
            log.error("Illegal region id. The region with id '" + id + "' isn`t contains in " +
                              "repository. Before calling this method need to check is repository" +
                              " contains the region with the calling id.");
            throw new IllegalArgumentException();
        }
    }

    public List<Region> getAllRegions() {
        log.debug("Getting list of all regions from repository.");
        List<Region> regionList = regionRepository.getAll();
        return regionList;
    }

    public boolean isRepositoryContainsRegionWithName(String name) {
        log.debug("Start to check is the region with the name '" + name +
                          "' contains in repository.");
        generateRegionsMapWithKeyNames();
        log.debug("Check result: " + regionsMapWithKeyNames.containsKey(name));
        return regionsMapWithKeyNames.containsKey(name);
    }

    public boolean isRepositoryContainsRegionWithId(Long id) {
        log.debug("Start to check is the region with the id '" + id + "' contains in repository.");
        generateRegionMapWithKeyIds();
        log.debug("Check result: " + regionMapWithKeyIds.containsKey(id));
        return regionMapWithKeyIds.containsKey(id);
    }

    private void generateRegionsMapWithKeyNames() {
        log.debug("Generate regions map whit region`s names as the map keys");
        regionsMapWithKeyNames = new HashMap<>();
        List<Region> regionsList = regionRepository.getAll();
        if (regionsList.size() > 0) {
            for (Region region : regionsList) {
                regionsMapWithKeyNames.put(region.getName(), region);
            }
        }
    }

    private void generateRegionMapWithKeyIds() {
        log.debug("Generate regions map whit region`s ids as the map keys");
        regionMapWithKeyIds = new HashMap<>();
        List<Region> regionsList = regionRepository.getAll();
        if (regionsList.size() > 0) {
            for (Region region : regionsList) {
                regionMapWithKeyIds.put(region.getId(), region);
            }
        }
    }


}
