package com.valentin_nikolaev.javacore.finalWork.repository;

public interface RepositoryFactory {

    UserRepository getUserRepository();

    PostRepository gerPostRepository();

    RegionRepository getRegionRepository();

}
