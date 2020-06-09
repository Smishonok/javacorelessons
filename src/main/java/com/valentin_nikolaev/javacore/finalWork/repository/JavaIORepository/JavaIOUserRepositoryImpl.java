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
            writer.write(this.prepareDataForSerialisation(user));
        } catch (IOException e) {
            log.error("Can`t write the user`s data into repository file: " + e.getMessage());
        }
    }

    @Override
    public User get(Long id) throws IllegalArgumentException {
        Optional<User> user = Optional.empty();
        try {
            user = Files.lines(usersRepositoryPath, Charset.forName("UTF-8")).filter(
                    userData->this.parseUserId(userData) == id).map(this::parseUser).findFirst();
        } catch (IOException e) {
            log.error("Can`t read repository file with users data: " + e.getMessage());
        }

        if (! user.isEmpty()) {
            return user.get();
        } else {
            throw new IllegalArgumentException(
                    "User with id '" + id + "' does not contain in database.");
        }
    }

    @Override
    public void remove(Long id) {
        List<String> usersList = getUsersListExcludeUserWith(id);

        try (BufferedWriter writer = Files.newBufferedWriter(usersRepositoryPath,
                                                             Charset.forName("UTF-8"),
                                                             StandardOpenOption.WRITE)) {
            for (String userData : usersList) {
                writer.write(userData);
            }
        } catch (IOException e) {
            log.error("Can`t write in repository file with users data: " + e.getMessage());
        }
    }

    private List<String> getUsersListExcludeUserWith(long id) {
        List<String> usersList = new ArrayList<>();
        try {
            usersList = Files.lines(usersRepositoryPath, Charset.forName("UTF-8")).filter(
                    usersData->this.parseUserId(usersData) != id).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with users data: " + e.getMessage());
        }
        return usersList;
    }

    @Override
    public List<User> getAll() {
        return getUsersListExcludeUserWith(0).stream().map(this::parseUser).collect(
                Collectors.toList());
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

    @Override
    public boolean isExists(Long id) {
        boolean isExist = false;
        try {
            isExist = Files.lines(usersRepositoryPath).filter(
                    userData->this.parseUserId(userData) == id).findFirst().isEmpty();
        } catch (IOException e) {
            log.error("Users`s repository file can`t be opened and read: " + e.getMessage());
        }
        return isExist;
    }

    private String prepareDataForSerialisation(User user) {
        return "User`s id:" + user.getId() + ";" + "User`s first name:" + user.getFirstName() +
                ";" +
                "User`s last name:" + user.getLastName() + ";" + "User`s region Id:" +
                user.getRegion().getId() + ";" + "User`s role:" + user.getRole().toString() + ";\n";
    }

    private long parseUserId(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User`s id:");
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

        scanner.findInLine("User`s first name:");
        return scanner.next();
    }

    private String parseUserLastName(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User`s last name:");
        return scanner.next();
    }

    private Region parseRegion(String userData) {
        Scanner scanner = new Scanner(userData);
        scanner.useDelimiter(";");

        scanner.findInLine("User`s region:");
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

        scanner.findInLine("User`s role:");
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
