package com.valentin_nikolaev.javacore.finalWork.controller;

import com.valentin_nikolaev.javacore.finalWork.models.User;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryManager;
import com.valentin_nikolaev.javacore.finalWork.repository.UserRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    static Logger log = Logger.getLogger(UserController.class.getName());

    private Map<String, User> usersBuffer;

    private UserRepository   userRepository;
    private PostController   postController;
    private RegionController regionController;

    public UserController() throws ClassNotFoundException {
        this.postController   = new PostController();
        this.regionController = new RegionController();
        initUserRepository();
        this.usersBuffer = new HashMap<>();
    }

    private void initUserRepository() throws ClassNotFoundException {
        log.debug("Starting initialisation of User repository");
        userRepository = RepositoryManager.getRepositoryFactory().getUserRepository();
        log.debug("User repository implementation is: "+ userRepository.getClass().getName());
    }



    public void addUser(String firstName,String lastName, String role, String region) {
        User user = new User(firstName, lastName);
        user.changeUserRole(role);

    }

    public void changeUserData() {

    }

    public void deleteUser(String userId) {

    }



}
