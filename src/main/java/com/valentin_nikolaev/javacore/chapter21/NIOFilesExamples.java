package com.valentin_nikolaev.javacore.chapter21;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;


enum UserGender {
    male, female,
}

class User {

    private String     name;
    private int        age;
    private UserGender gender;

    public User(String name, int age, String gender) {
        this.name   = name;
        this.age    = age;
        this.gender = UserGender.valueOf(gender);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    boolean isFemale() {
        return this.gender == UserGender.female;
    }

    int compareByAge(User user) {
        return this.getAge() - user.getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && this.name.equals(user.getName()) && this.gender.equals(
                user.getGender());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.age + this.gender.hashCode();
    }

    @Override
    public String toString() {
        String userInfo = "User name: " + this.name + ";User age: " + this.age + ";User gender: " +
                this.gender.toString() + ";\n";
        return userInfo;
    }
}

public class NIOFilesExamples {

    static User parseUser(String userData) {
        Scanner scanner = new Scanner(userData);

        List<String> parameters = new ArrayList<>();
        scanner.useDelimiter(";");

        scanner.findInLine("User name: ");
        if (scanner.hasNext()) {
            parameters.add(scanner.next());
        }

        scanner.findInLine("User age: ");
        if (scanner.hasNext()) {
            parameters.add(scanner.next());
        }

        scanner.findInLine("User gender: ");
        if (scanner.hasNext()) {
            parameters.add(scanner.next());
        }

        User user = new User(parameters.get(0), Integer.parseInt(parameters.get(1)),
                             parameters.get(2));
        return user;
    }

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new User("Valentin", 32, "male"));
        users.add(new User("Kate", 24, "female"));
        users.add(new User("Mary", 45, "female"));
        users.add(new User("Michel", 54, "male"));
        users.add(new User("Mikel", 19, "male"));
        users.add(new User("Kate", 43, "female"));


        Path appRootPath = Paths.get(ClassLoader.getSystemResource("").getPath()
                                                .replaceFirst("/", ""));
        Path NIOResources = appRootPath.resolve("NIOResources");

        if (! Files.exists(NIOResources)) {
            try {
                Files.createDirectory(NIOResources);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path usersFile = NIOResources.resolve("Users");
        try (BufferedWriter writer = Files.newBufferedWriter(usersFile, Charset.forName("UTF-8"),
                                                             StandardOpenOption.CREATE,
                                                             StandardOpenOption.WRITE)) {
            for (User user : users) {
                writer.write(user.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<User> loadedUsers = null;
        try (BufferedReader reader = Files.newBufferedReader(usersFile, Charset.forName("UTF-8"))) {
            loadedUsers = reader.lines().map(NIOFilesExamples::parseUser).collect(
                    Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (loadedUsers != null) {
            loadedUsers.forEach(System.out::print);
        }

    }


}
