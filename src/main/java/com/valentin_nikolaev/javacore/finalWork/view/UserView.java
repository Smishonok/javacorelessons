package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;
import com.valentin_nikolaev.javacore.finalWork.controller.UserController;
import com.valentin_nikolaev.javacore.finalWork.models.Role;

import java.util.ArrayList;
import java.util.List;

public class UserView {

    private UserController   userController;
    private RegionController regionController;

    private final String ADD    = "add";
    private final String GET    = "get";
    private final String CHANGE = "change";
    private final String REMOVE = "remove";

    private final String HELP = "help";

    public UserView() throws ClassNotFoundException {
        this.userController   = new UserController();
        this.regionController = new RegionController();
    }

    public void action(String action, String[] options) {
        switch (action) {
            case HELP:
                getMainHelp();
                break;
            case ADD:
                addUser(options);
                break;
            case GET:

        }
    }

    private void getMainHelp() {
        String helpInfo = "This is the part of the console app in which you can add, change and " +
                "remove user data from repository. The main commands are:\n" +
                "\tadd - adding new user;\n" + "\tget - getting user data from repository;\n" +
                "\tchange - changing user data in repository\n" +
                "\tremove - removing user from repository;\n" +
                "\n\tCalling \"help\" after each of commands calls the help`s information for the" +
                " corresponding command.";
        System.out.println(helpInfo);
    }

    private void addUser(String[] userData) {
        int userDataLength = userData.length;

        if (userData.length == 1 && userData[0].equals(HELP)) {
            userDataLength = - 1;
        }
        switch (userDataLength) {
            case - 1:
                getADDHelp();
                break;
            case 3:
                addUser1(userData);
                break;
            case 4:
                addUser2(userData);
                break;
            default:
                System.out.println(
                        "Invalid user data. Pleas, check user data and try again, or call " +
                                "\"add help\".");
                break;
        }
    }

    private void getUserData(String[] userData) {

    }

    private void getADDHelp() {
        String helpInfo = "For adding user in repository it can be used two formats of data:\n" +
                "\tVariant 1: [user first name] [user last name] [user region]\n" +
                "\tVariant 2: [user first name] [user last name] [user role] [user region]\n" +
                "For example:\n" + "\t Variant 1: Ivan Ivanov Moscow\n" +
                "\t Variant 2: Ivan Ivanov admin Moscow\n";
    }

    private void addUser1(String[] userData) {
        String userFirstName = userData[0];
        String userLastName  = userData[1];
        String regionName    = userData[2];

        if (isRegionNameValid(regionName)) {
            this.userController.addUser(userFirstName, userLastName, regionName);
        } else {
            System.out.println(
                    "Invalid region name. The region with name: " + regionName + " is not " +
                            "contains in the repository.");
        }
    }

    private void addUser2(String[] userData) {
        String userFirstName = userData[0];
        String userLastName  = userData[1];
        String userRole      = userData[2];
        String regionName    = userData[3];

        if (! isRegionNameValid(regionName)) {
            System.out.println(
                    "Invalid region name. The region with name: " + regionName + " is not " +
                            "contains in the repository.");
        }

        if (! isRoleNameValid(userRole)) {
            System.out.println("Invalid role name. User`s role can be:\n");
            List<Role> userRoles = List.of(Role.values());
            for (Role role : userRoles) {
                System.out.println("\t" + role.toString());
            }
        }

        if (isRegionNameValid(regionName) && isRoleNameValid(userRole)) {
            this.userController.addUser(userFirstName, userLastName, userRole, regionName);
        }
    }

    private boolean isRegionNameValid(String regionName) {
        return this.regionController.getRegionByName(regionName).isPresent();
    }

    private boolean isRoleNameValid(String roleName) {
        List<Role> roles = List.of(Role.values());
        return roles.stream().anyMatch(role->role.toString().equals(roleName));
    }

}
