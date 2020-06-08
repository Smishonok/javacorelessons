package com.valentin_nikolaev.javacore.finalWork.repository;

public interface RepositoryFactory {

    UserRepository getUserRepository() throws ClassNotFoundException;

    PostRepository getPostRepository();

    RegionRepository getRegionRepository();

}
