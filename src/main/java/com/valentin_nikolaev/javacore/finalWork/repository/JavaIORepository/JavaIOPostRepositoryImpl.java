package com.valentin_nikolaev.javacore.finalWork.repository.JavaIORepository;

import com.valentin_nikolaev.javacore.finalWork.models.Post;
import com.valentin_nikolaev.javacore.finalWork.repository.PostRepository;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JavaIOPostRepositoryImpl implements PostRepository {

    static Logger log = Logger.getLogger(JavaIOPostRepositoryImpl.class);

    private Path postsRepositoryPath;


    public JavaIOPostRepositoryImpl(Path repositoryRootPath) {
        postsRepositoryPath = repositoryRootPath.resolve("postsRepository.txt");
        createPostRepository();
    }

    private void createPostRepository() {
        log.debug("Checking is repository file with posts data exists.");
        if (! Files.exists(this.postsRepositoryPath)) {
            log.debug(
                    "Posts repository does not exist, started the creation of a repository file.");
            try {
                Files.createFile(this.postsRepositoryPath);
            } catch (IOException e) {
                log.debug("File \"postsRepository.txt\" can`t be created: " + e.getMessage());
            }
            log.debug("The repository file with posts data created successfully");
        } else {
            log.debug("The repository file with posts data exists.");
        }
    }

    @Override
    public void add(Post post) {
        try (BufferedWriter writer = Files.newBufferedWriter(postsRepositoryPath,
                                                             Charset.forName("UTF-8"),
                                                             StandardOpenOption.WRITE,
                                                             StandardOpenOption.APPEND)) {
            writer.write(this.prepareDataForSerialisation(post));
        } catch (IOException e) {
            log.error("Can`t write the post`s data into repository file: " + e.getMessage());
        }
    }

    @Override
    public Post get(Long id) {
        Optional<Post> post = Optional.empty();
        try {
            post = Files.lines(postsRepositoryPath).filter(postData->parsePostId(postData) == id)
                        .map(this::parsePost).findFirst();
        } catch (IOException e) {
            log.error("Can`t read repository file with posts data: " + e.getMessage());
        }

        if (! post.isEmpty()) {
            return post.get();
        } else {
            throw new IllegalArgumentException(
                    "Post with id: " + id + " does not contain in repository.");
        }
    }

    @Override
    public void remove(Long id) {
        List<String> postsList = getPostsListExcludePostWithId(id);

        try {
            BufferedWriter writer = Files.newBufferedWriter(postsRepositoryPath,
                                                            Charset.forName("UTF-8"),
                                                            StandardOpenOption.WRITE);
            for (String post : postsList) {
                writer.write(post);
            }
        } catch (IOException e) {
            log.error("Can`t write the post`s data into repository file: " + e.getMessage());
        }
    }

    private List<String> getPostsListExcludePostWithId(long id) {
        List<String> postsList = new ArrayList<>();
        try {
            postsList = Files.lines(postsRepositoryPath).filter(
                    postData->parsePostId(postData) != id).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with posts data: " + e.getMessage());
        }
        return postsList;
    }

    @Override
    public List<Post> getAll() {
        return getPostsListExcludePostWithId(0).stream().map(this::parsePost).collect(
                Collectors.toList());
    }

    @Override
    public void removeAll() {
        if (Files.exists(postsRepositoryPath)) {
            try {
                Files.delete(postsRepositoryPath);
            } catch (IOException e) {
                log.error("The repository file can`t be deleted: " + e.getMessage());
            }
        }

        try {
            Files.createFile(postsRepositoryPath);
        } catch (IOException e) {
            log.error("The repository file can`t be created: " + e.getMessage());
        }
    }

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        List<Post> userPostsList = new ArrayList<>();
        try {
            userPostsList = Files.lines(postsRepositoryPath).filter(
                    postData->parseUserId(postData) == userId).map(this::parsePost).collect(
                    Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with posts data: " + e.getMessage());
        }
        return userPostsList;
    }

    @Override
    public void removePostsByUserId(Long userId) {
        List<String> postsList = getPostsListWithOutCreatedByUser(userId);

        try {
            BufferedWriter writer = Files.newBufferedWriter(postsRepositoryPath,
                                                            Charset.forName("UTF-8"),
                                                            StandardOpenOption.WRITE);
            for (String post : postsList) {
                writer.write(post);
            }
        } catch (IOException e) {
            log.error("Can`t write the post`s data into repository file: " + e.getMessage());
        }
    }

    private List<String> getPostsListWithOutCreatedByUser(long userId) {
        List<String> postsList = new ArrayList<>();
        try {
            postsList = Files.lines(postsRepositoryPath).filter(
                    postData->parseUserId(postData) != userId).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Can`t read repository file with posts data: " + e.getMessage());
        }
        return postsList;
    }

    @Override
    public boolean isExists(Long postId) {
        boolean isExists = false;
        try {
            isExists = Files.lines(postsRepositoryPath).filter(
                    postData->parsePostId(postData) == postId).findAny().isEmpty();
        } catch (IOException e) {
            log.error("Can`t read repository file with posts data: " + e.getMessage());
        }
        return isExists;
    }

    private String prepareDataForSerialisation(Post post) {
        long   postId  = post.getId();
        long   userId  = post.getUserId();
        String content = post.getContent();

        long creationTimeInEpochSeconds = post.getCreatingDateAndTime().toEpochSecond(
                ZoneOffset.UTC);
        long updatingTimeInEpochSeconds = post.getUpdatingDateAndTime().toEpochSecond(
                ZoneOffset.UTC);


        return "Post`s id:" + postId + ";" + "User`s id:" + userId + ";" + "Post`s creation date:" +
                creationTimeInEpochSeconds + ";" + "Post`s updating date:" +
                updatingTimeInEpochSeconds + ";" + "Post`s content:" + content + ";\n";
    }

    private long parsePostId(String postData) {
        Scanner scanner = new Scanner(postData);
        scanner.useDelimiter(";");

        scanner.findInLine("Post`s id:");
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the post Id.");
        }
    }

    private long parseUserId(String postData) {
        Scanner scanner = new Scanner(postData);
        scanner.useDelimiter(";");

        scanner.findInLine("User`s id:");
        if (scanner.hasNextLong()) {
            return scanner.nextLong();
        } else {
            throw new IllegalArgumentException(
                    "Invalid data. The string does not contain the user Id.");
        }
    }

    private LocalDateTime parseDateTime(String dateType, String postData) {
        Scanner scanner = new Scanner(postData);
        scanner.useDelimiter(";");

        scanner.findInLine(dateType);
        if (scanner.hasNextLong()) {
            return LocalDateTime.ofEpochSecond(scanner.nextLong(), 0, ZoneOffset.UTC);
        } else {
            throw new IllegalArgumentException("Invalid data. The string does not contain the" +
                                                       dateType.toLowerCase().replace(":", "") +
                                                       ".");
        }

    }

    private String parseContent(String postData) {
        Scanner scanner = new Scanner(postData);
        scanner.useDelimiter(";");

        scanner.findInLine("Post`s content:");
        String content = "";
        content += scanner.next();
        return content;
    }

    private Post parsePost(String postData) {
        long          postId       = parsePostId(postData);
        long          userId       = parseUserId(postData);
        LocalDateTime creationDate = parseDateTime("Post`s creation date:", postData);
        LocalDateTime updatingDate = parseDateTime("Post`s updating date:", postData);
        String        content      = parseContent(postData);

        return new Post(postId, userId, content, creationDate, updatingDate);
    }
}
