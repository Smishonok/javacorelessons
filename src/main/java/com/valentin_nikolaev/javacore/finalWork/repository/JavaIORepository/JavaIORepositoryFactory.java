package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import com.valentin_nikolaev.javacore.finalWork.repository.PostRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryFactory;
import com.valentin_nikolaev.javacore.finalWork.repository.UserRepository;

public class JavaIORepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository getUserRepository() {
        return new JavaIOUserRepositoryImpl();
    }

    @Override
    public PostRepository gerPostRepository() {
        return new JavaIOPostRepositoryImpl();
    }

    @Override
    public RegionRepository getRegionRepository() {
        return new JavaIORegionRepositoryImpl();
    }
}
