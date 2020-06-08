package com.valentin_nikolaev.javacore.finalWork.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long   id;
    private String firstName;
    private String lastName;
    private Region region;
    private Role   role;
    List<Post> posts;

    private static long groupId = 0;
    private final  Role DEFAULT = Role.USER;

    public User(String firstName, String lastName, Region region) {
        this.id        = ++ groupId;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.region    = region;
        this.role      = DEFAULT;
        this.posts     = new ArrayList<>();
    }

    public User(String firstName, String lastName, Region region, Role role) {
        this(firstName, lastName, region);
        this.role = role;
    }

    public User(long id, String firstName, String lastName, Region region, Role role,
                 List<Post> posts) {
        this.id        = id;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.region    = region;
        this.role      = role;
        this.posts     = posts;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Region getRegion() {
        return region;
    }

    public Role getRole() {
        return role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void changeUserRole(String role) {
        this.role = Role.valueOf(role);
    }

    @Override
    public int hashCode() {
        int hash = this.firstName.hashCode() + this.lastName.hashCode() * 3 + region.hashCode() +
                this.role.toString().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.hashCode() != obj.hashCode()) {
            return false;
        }

        if (! (obj instanceof User)) {
            return false;
        }

        User comparingObj = (User) obj;
        return this.firstName.equals(comparingObj.firstName) && this.lastName.equals(
                comparingObj.lastName) && this.role.toString().equals(comparingObj.role.toString());
    }
}
