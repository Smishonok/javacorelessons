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

    private Path             usersRepositoryPath;
    private PostRepository   postRepository;
    private RegionRepository regionRepository;

    public JavaIOUserRepositoryImpl(Path repositoryRootPath) throws ClassNotFoundException {
        this.usersRepositoryPath = repositoryRootPath.resolve("userRepository.txt");
        createUserRepository();
        postRepository   = RepositoryManager.getRepositoryFactory().getPostRepository();
        regionRepository = RepositoryManager.getRepositoryFactory().getRegionRepository();
    }

    private void createUserRepository() {
        log.debug("Checking is repository file with users data exists.");
        if (! Files.exists(this.usersRepositoryPath)) {
            log.debug(
                    "Users repository does not exist, started the creation of a repository file.");
            try {
                Files.createFile(this.usersRepositoryPath);
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
        try (BufferedWriter writer = Files.newBufferedWriter(usersRepositoryPath,
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
            user = Files.lines(usersRepositoryPath, Charset.forName("UTF-8")).filter(
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
            users = Files.lines(usersRepositoryPath, Charset.forName("UTF-8")).filter(
                    usersData->this.parseUserId(usersData) != id).map(this::parseUser).collect(
                    Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with users data: " + e.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(usersRepositoryPath,
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
            users = Files.lines(usersRepositoryPath).map(this::parseUser).collect(
                    Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with users data: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void removeAll() {
        if (Files.exists(usersRepositoryPath)) {
            try {
                Files.delete(usersRepositoryPath);
            } catch (IOException e) {
                log.error("The repository file can`t be deleted: " + e.getMessage());
            }
        }

        try {
            Files.createFile(usersRepositoryPath);
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
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the user Id.");
        }
    }

    private String parseUserFirstName(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User first name:");
        return scanner.next();
    }

    private String parseUserLastName(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User last name:");
        return scanner.next();
    }

    private Region parseRegion(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User region:");
        if (scanner.hasNextLong()) {
            return regionRepository.get(scanner.nextLong());
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the region Id.");
        }
    }

    private Role parseRole(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User role:");
        if (scanner.hasNext()) {
            return Role.valueOf(scanner.next());
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the user role.");
        }
    }

    private User parseUser(String userData) {
        if (userData.isBlank() || userData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        long       id        = parseUserId(userData);
        String     firstName = parseUserFirstName(userData);
        String     lastName  = parseUserLastName(userData);
        Region     region    = parseRegion(userData);
        Role       role      = parseRole(userData);
        List<Post> posts     = this.postRepository.getPostsByUserId(id);

        return new User(id, firstName, lastName, region, role, posts);
    }


}
