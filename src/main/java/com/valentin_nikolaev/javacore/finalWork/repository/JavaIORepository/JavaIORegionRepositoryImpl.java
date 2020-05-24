package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;

import java.io.File;
import java.util.List;

public class JavaIORegionRepositoryImpl implements RegionRepository {

    private File regionRepository = new File(
            "src/main/resources/FileRepository/regionRepository.txt");

    @Override
    public void add(Region region) {
        String regionData = region.getId()+","+region.getName();



    }

    @Override
    public Region get(Long id) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<Region> getAll() {
        return null;
    }

    @Override
    public void removeAll() {

    }
}
