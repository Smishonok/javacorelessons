package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import com.valentin_nikolaev.javacore.finalWork.models.Post;
import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.models.Role;
import com.valentin_nikolaev.javacore.finalWork.models.User;
import com.valentin_nikolaev.javacore.finalWork.repository.PostRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RegionRepository;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryManager;
import com.valentin_nikolaev.javacore.finalWork.repository.UserRepository;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class JavaIOUserRepositoryImpl implements UserRepository {

    public static Logger log = Logger.getLogger(JavaIOUserRepositoryImpl.class);

    private Path             usersRepository;
    private PostRepository   postRepository;
    private RegionRepository regionRepository;

    public JavaIOUserRepositoryImpl(Path repositoryRootPath) throws ClassNotFoundException {
        this.usersRepository = repositoryRootPath.resolve("userRepository.txt");
        createUserRepository();
        postRepository   = RepositoryManager.getRepositoryFactory().getPostRepository();
        regionRepository = RepositoryManager.getRepositoryFactory().getRegionRepository();
    }

    private void createUserRepository() {
        log.debug("Checking is repository file with users data exists.");
        if (! Files.exists(this.usersRepository)) {
            log.debug(
                    "Users repository does not exist, started the creation of a repository file.");
            try {
                Files.createFile(this.usersRepository);
            } catch (IOException e) {
                log.debug("File \"userRepository.txt\" can`t be created: " + e.getMessage());
            }
            log.debug("The repository file with users data created successfully");
        } else {
            log.debug("The repository file with users data exists.");
        }
    }

    @Override
    public void add(User user) {
        try (BufferedWriter writer = Files.newBufferedWriter(usersRepository,
                                                             Charset.forName("UTF-8"),
                                                             StandardOpenOption.WRITE,
                                                             StandardOpenOption.APPEND)) {
            writer.write(this.getDataForSerialisation(user));
        } catch (IOException e) {
            log.error("Can`t write the user`s data into repository file: " + e.getMessage());
        }
    }

    @Override
    public User get(Long id) {
        User user = null;
        try {
            user = Files.lines(usersRepository, Charset.forName("UTF-8")).filter(
                    userData->this.parseUserId(userData) == id).map(this::parseUser).collect(
                    Collectors.toList()).get(0);
        } catch (IOException e) {
            log.error("Can`t read repository file with users data: " + e.getMessage());
        }

        if (user != null) {
            return user;
        } else {
            throw new IllegalArgumentException(
                    "User with id '" + id + "' does not contain in database.");
        }
    }

    @Override
    public void remove(Long id) {
        List<User> users = null;
        try {
            users = Files.lines(usersRepository, Charset.forName("UTF-8")).filter(
                    usersData->this.parseUserId(usersData) != id).map(this::parseUser).collect(
                    Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with users data: " + e.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(usersRepository,
                                                             Charset.forName("UTF-8"),
                                                             StandardOpenOption.WRITE)) {
            if (users != null) {
                for (User user : users) {
                    writer.write(this.getDataForSerialisation(user));
                }
            }
        } catch (IOException e) {
            log.error("Can`t write in repository file with users data: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            users = Files.lines(usersRepository).map(this::parseUser).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with users data: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void removeAll() {
        if (Files.exists(usersRepository)) {
            try {
                Files.delete(usersRepository);
            } catch (IOException e) {
                log.error("The repository file can`t be deleted: " + e.getMessage());
            }
        }

        try {
            Files.createFile(usersRepository);
        } catch (IOException e) {
            log.error("The repository file can`t be created: " + e.getMessage());
        }
    }

    private String getDataForSerialisation(User user) {
        return "User id:" + user.getId() + ";" + "User first name:" + user.getFirstName() + ";" +
                "User last name:" + user.getLastName() + ";" + "User region Id:" +
                user.getRegion().getId() + ";" + "User role:" + user.getRole().toString() + ";";
    }

    private long parseUserId(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User id:");
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        } else {
            throw new IllegalArgumentException("Input string does not contains user id.");
        }
    }

    private User parseUser(String userData) {
        long       id;
        String     firstName = "";
        String     lastName  = "";
        Region     region;
        Role       role;
        List<Post> posts;

        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User id:");
        if (scanner.hasNextLong()) {
            id = scanner.nextLong();
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the user Id.");
        }

        scanner.findInLine("User first name:");
        firstName = scanner.next();

        scanner.findInLine("User last name:");
        lastName = scanner.next();

        scanner.findInLine("User region:");
        if (scanner.hasNextLong()) {
            region = regionRepository.get(scanner.nextLong());
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the region Id.");
        }

        scanner.findInLine("User role:");
        if (scanner.hasNext()) {
            role = Role.valueOf(scanner.next());
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the user role.");
        }

        posts = this.postRepository.getPostsByUserId(id);

        return new User(id, firstName, lastName, region, role, posts);
    }


}
