package com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;
import com.valentin_nikolaev.javacore.finalWork.controller.UserController;
import com.valentin_nikolaev.javacore.finalWork.models.Role;

import java.util.List;

public class AddRequestHandler extends UserRequestHandler {

    @Override
    public void handleRequest(String action, String[] options) throws ClassNotFoundException {
        if (ADD.equals(action)) {
            addUser(options);
        } else {
            getNextHandler(action, options);
        }
    }

    private void addUser(String[] options) throws ClassNotFoundException {
        int userDataLength = options.length;

        if (options.length == 1 && options[0].equals(HELP)) {
            userDataLength = - 1;
        }

        switch (userDataLength) {
            case - 1:
                getHelpForAddingRequest();
                break;
            case 3:
                addUserShort(options);
                break;
            case 4:
                addUserLong(options);
                break;
            default:
                System.out.println(
                        "Invalid user data. Pleas, check user data and try again, or call " +
                                "\"add help\".");
                break;
        }
    }

    private void addUserShort(String[] options) throws ClassNotFoundException {
        String userFirstName = options[0];
        String userLastName  = options[1];
        String regionName    = options[2];

        if (isRegionNameValid(regionName)) {
            UserController userController = new UserController();
            userController.addUser(userFirstName, userLastName, regionName);
        } else {
            System.out.println(
                    "Invalid region name. The region with name: " + regionName + " is not " +
                            "contains in the repository.");
        }
    }

    private void addUserLong(String[] options)throws ClassNotFoundException {
        String userFirstName = options[0];
        String userLastName  = options[1];
        String userRole      = options[2];
        String regionName    = options[3];

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
            UserController userController = new UserController();
            userController.addUser(userFirstName, userLastName, userRole, regionName);
        }
    }

    private void getHelpForAddingRequest() {
        String helpInfo = "For adding user in repository it can be used two formats of data:\n" +
                "\tVariant 1: [user first name] [user last name] [user region]\n" +
                "\tVariant 2: [user first name] [user last name] [user role] [user region]\n" +
                "For example:\n" +
                "\t Variant 1: Ivan Ivanov Moscow\n" +
                "\t Variant 2: Ivan Ivanov admin Moscow\n";
        System.out.println(helpInfo);
    }

    private boolean isRegionNameValid(String regionName) throws ClassNotFoundException {
        RegionController regionController = new RegionController();
        return regionController.getRegionByName(regionName).isPresent();
    }

    private boolean isRoleNameValid(String roleName) {
        List<Role> roles = List.of(Role.values());
        return roles.stream().anyMatch(role->role.toString().equals(roleName));
    }
}
