package com.valentin_nikolaev.javacore.finalWork.controller;

import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryManager;
import org.apache.log4j.Logger;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RegionController {

    static Logger log = Logger.getLogger(RegionController.class.getName());

    private RegionRepository    regionRepository;

    public RegionController() throws ClassNotFoundException {
        initRegionRepository();
    }

    private void initRegionRepository() throws ClassNotFoundException {
        log.debug("Starting initialisation of Region repository");
        regionRepository = RepositoryManager.getRepositoryFactory().getRegionRepository();
        log.debug("Region repository implementation is: " + regionRepository.getClass().getName());
    }

    public void addRegion(String name) {
        log.debug("Adding new region into repository.");
        Region region = new Region(name);
        regionRepository.add(region);
        log.debug("Adding the new region into the repository ended successfully.");
    }

    public Optional<Region> getRegionById(String regionId) {
        long id = Long.parseLong(regionId);
        Optional<Region> region = Optional.empty();
        if (this.regionRepository.contains(id)) {
            region = Optional.of(this.regionRepository.get(id));
        }
        return region;
    }

    public Optional<Region> getRegionByName(String regionName) {
        Optional<Region> requestedRegion = Optional.empty();
        List<Region>     regionsList     = this.regionRepository.getAll();

        int indexOfRequestedRegion = - 1;
        for (int i = 0; i < regionsList.size(); i++) {
            if (regionsList.get(i).getName().equals(regionName)) {
                indexOfRequestedRegion = i;
            }
        }

        if (indexOfRequestedRegion != - 1) {
            requestedRegion = Optional.of(regionsList.get(indexOfRequestedRegion));
        }

        return requestedRegion;
    }

    public void changeRegionName(String regionId, String newRegionName) {
        long id = Long.parseLong(regionId);
        if (this.regionRepository.contains(id)) {
            Region region = this.regionRepository.get(id);
            region.setName(newRegionName);
            this.regionRepository.change(region);
        }
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

    public List<Region> getAllRegions() {
        log.debug("Getting list of all regions from repository.");
        List<Region> regionList = regionRepository.getAll();
        return regionList;
    }
}
