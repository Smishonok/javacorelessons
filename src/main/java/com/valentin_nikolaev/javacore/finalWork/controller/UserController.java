package com.valentin_nikolaev.javacore.finalWork.controller;

import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.models.Role;
import com.valentin_nikolaev.javacore.finalWork.models.User;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryManager;
import com.valentin_nikolaev.javacore.finalWork.repository.UserRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserController {

    static Logger log = Logger.getLogger(UserController.class.getName());

    private UserRepository   usersRepository;
    private RegionController regionController;

    public UserController() throws ClassNotFoundException {
        this.regionController = new RegionController();
        initUserRepository();
    }

    private void initUserRepository() throws ClassNotFoundException {
        log.debug("Starting initialisation of User repository");
        usersRepository = RepositoryManager.getRepositoryFactory().getUserRepository();
        log.debug("User repository implementation is: " + usersRepository.getClass().getName());
    }

    public void addUser(String firstName, String lastName, String regionName) {
        Region region = regionController.getRegionByName(regionName).get();
        User   user   = new User(firstName, lastName, region);
        this.usersRepository.add(user);
    }

    public void addUser(String firstName, String lastName, String roleName, String regionName) {
        Region region = regionController.getRegionByName(regionName).get();
        Role   role   = Role.valueOf(roleName);
        User   user   = new User(firstName, lastName, region, role);
        this.usersRepository.add(user);
    }

    public Optional<User> getUserById(String id) {
        long           userId = Long.parseLong(id);
        Optional<User> user = this.usersRepository.contains(userId) ? Optional.of(
                this.usersRepository.get(userId)) : Optional.empty();

        return user;
    }

    public List<User> getAllUsersList() {
        return this.usersRepository.getAll();
    }

    public List<User> getUsersWithFirstName(String firstName) {
        return this.usersRepository.getAll().stream().filter(
                user->user.getFirstName().equals(firstName)).collect(Collectors.toList());
    }

    public List<User> getUsersWithLastName(String lastName) {
        return this.usersRepository.getAll().stream().filter(
                user->user.getLastName().equals(lastName)).collect(Collectors.toList());
    }

    public List<User> getUsersWithRole(String roleName) {
        return this.usersRepository.getAll().stream().filter(
                user->user.getRole().toString().equals(roleName)).collect(Collectors.toList());
    }

    public List<User> getUsersFrom(String regionName) {
        return this.usersRepository.getAll().stream().filter(
                user->user.getRegion().getName().equals(regionName)).collect(Collectors.toList());
    }

    public void changeUserFirstName(String userId, String newUserFirstName) {
        long id = Long.parseLong(userId);
        if (this.usersRepository.contains(id)) {
            User user = this.usersRepository.get(id);
            user.setFirstName(newUserFirstName);
            this.usersRepository.change(user);
        }
    }

    public void changeUserLastName(String userId, String newUserLastName) {
        long id = Long.parseLong(userId);
        if (this.usersRepository.contains(id)) {
            User user = this.usersRepository.get(id);
            user.setLastName(newUserLastName);
            this.usersRepository.change(user);
        }
    }

    public void changeUserRole(String userId, String newUserRole) {
        long id = Long.parseLong(userId);
        if (this.usersRepository.contains(id)) {
            User user = this.usersRepository.get(id);
            user.changeUserRole(newUserRole);
            this.usersRepository.change(user);
        }
    }

    public void changeUserRegion(String userId, String regionName) {
        long id = Long.parseLong(userId);
        Region region = regionController.getRegionByName(regionName).get();
        if (this.usersRepository.contains(id)) {
            User   user   = this.usersRepository.get(id);
            user.setRegion(region);
            this.usersRepository.change(user);
        }
    }

    public void removeUser(String userId) {
        this.usersRepository.remove(Long.parseLong(userId));
    }

    public void removeAllUsers() {
        this.usersRepository.removeAll();
    }
}
