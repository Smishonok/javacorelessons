package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import com.valentin_nikolaev.javacore.finalWork.repository.PostRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryFactory;
import com.valentin_nikolaev.javacore.finalWork.repository.UserRepository;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class JavaIORepositoryFactory implements RepositoryFactory {

    public static Logger log = Logger.getLogger(JavaIORepositoryFactory.class);

    private Path repositoryRootPath;

    public JavaIORepositoryFactory() {
        getFilesRepositoryPath();
        createRepositoryDirectory();
    }

    @Override
    public UserRepository getUserRepository() throws ClassNotFoundException {
        return new JavaIOUserRepositoryImpl(repositoryRootPath);
    }

    @Override
    public PostRepository getPostRepository() {
        return new JavaIOPostRepositoryImpl(repositoryRootPath);
    }

    @Override
    public RegionRepository getRegionRepository() {
        return new JavaIORegionRepositoryImpl(repositoryRootPath);
    }

    private void getFilesRepositoryPath() {
        try {
            Path appRootPath = Path.of(ClassLoader.getSystemResource("").getPath()
                                                  .replaceFirst("/", ""));
            this.repositoryRootPath = appRootPath.resolve("FileRepository");
        } catch (InvalidPathException e) {
            log.error("Illegal path: " + e.getMessage());
        }
    }

    private void createRepositoryDirectory() {
        if (! Files.exists(repositoryRootPath)) {
            try {
                Files.createDirectory(repositoryRootPath);
            } catch (IOException e) {
                log.error("Directory can`t be creates: " + e.getMessage());
            }
        }
    }
}
