package com.valentin_nikolaev.javacore.chapter29;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        String userInfo =
                "User name: " + this.name + "\tUser age: " + this.age + "\tUser gender: " +
                        this.gender.toString();
        return userInfo;
    }
}


public class StreamsExamples1 {
    public static void main(String[] args) {

        List<User> users = new ArrayList<>();
        users.add(new User("Valentin", 32, "male"));
        users.add(new User("Kate", 24, "female"));
        users.add(new User("Mary", 45, "female"));
        users.add(new User("Michel", 54, "male"));
        users.add(new User("Mikel", 19, "male"));
        users.add(new User("Kate", 43, "female"));

        Stream<User> userStream = users.stream();
        List<User> maleUsers = userStream.filter(user->user.getGender() == UserGender.male).sorted(
                (u1, u2)->u1.getAge() - u2.getAge()).collect(Collectors.toList());
        maleUsers.stream().forEach(System.out::println);
        System.out.println();


        List<User> femaleUsers = users.stream().filter(User::isFemale).sorted(User::compareByAge)
                                      .collect(Collectors.toList());
        for (User u : femaleUsers) {
            System.out.println(u);
        }

        int femaleUsersNumber = (int) users.stream().filter(
                user->user.getGender() == UserGender.female).count();
        System.out.println("Female users number: " + femaleUsersNumber);

        int femaleUsersWithAgeMore = (int) users.stream().filter(User::isFemale).filter(
                user->user.getAge() > 30).count();
        System.out.println("Females with age more then 30 years: " + femaleUsersWithAgeMore);

        Optional<User> femaleWithMaxAge = users.stream().filter(User::isFemale).max(User::compareByAge);
        System.out.println("Female with max age is: \n\t"+femaleWithMaxAge.get().toString());

        List<String> usersGender = users.stream().map(user -> "\nUser name: "+user.getName()+
                "\tUser gender: "+user.getGender().toString()).collect(Collectors.toList());
        usersGender.stream().forEach(System.out::print);

    }


}
