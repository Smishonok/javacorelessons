package com.valentin_nikolaev.javacore.finalWork.controller;

import com.valentin_nikolaev.javacore.finalWork.models.Region;
import com.valentin_nikolaev.javacore.finalWork.models.Role;
import com.valentin_nikolaev.javacore.finalWork.models.User;
import com.valentin_nikolaev.javacore.finalWork.repository.RepositoryManager;
import com.valentin_nikolaev.javacore.finalWork.repository.UserRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    static Logger log = Logger.getLogger(UserController.class.getName());

    private UserRepository   usersRepository;
    private PostController   postController;
    private RegionController regionController;

    public UserController() throws ClassNotFoundException {
        this.postController   = new PostController();
        this.regionController = new RegionController();
        initUserRepository();
    }

    private void initUserRepository() throws ClassNotFoundException {
        log.debug("Starting initialisation of User repository");
        usersRepository = RepositoryManager.getRepositoryFactory().getUserRepository();
        log.debug("User repository implementation is: " + usersRepository.getClass().getName());
    }


    public void addUser(String firstName, String lastName, String roleName, String regionName) {
        Region region = regionController.getRegionByName(regionName);
        Role   role   = Role.valueOf(roleName);
        User   user   = new User(firstName, lastName, region, role);
        this.usersRepository.add(user);
    }


    public void changeUserData() {

    }

    public void deleteUser(String userId) {
        this.usersRepository.remove(Long.parseLong(userId));
    }




}
