package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;
import com.valentin_nikolaev.javacore.finalWork.controller.UserController;
import com.valentin_nikolaev.javacore.finalWork.models.Role;
import com.valentin_nikolaev.javacore.finalWork.models.User;

import java.util.List;
import java.util.Optional;

import static org.graalvm.compiler.options.OptionType.User;

public class UserView {

    private UserController   userController;
    private RegionController regionController;

    private final String ADD    = "add";
    private final String GET    = "get";
    private final String CHANGE = "change";
    private final String REMOVE = "remove";

    private final String HELP = "help";

    //Get method options
    private final String ID         = "id";
    private final String ALL        = "all";
    private final String FIRST_NAME = "name.first";
    private final String LAST_NAME  = "name.last";
    private final String ROLE       = "role";
    private final String Region     = "region";


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
                getUserData(options);
                break;
            case CHANGE:

                break;
            case REMOVE:

                break;
            default:
                System.out.println("Invalid request type.\n");
                getMainHelp();
        }
    }

    private void getUserData(String[] userData) {
        int    requestLength = userData.length;
        String request       = userData[0];

        switch (request) {
            case HELP:
                getGETHelp();
                break;
            case ID:
                if (userData.length == 2) {
                    getUserByID(userData[1]);
                } else {
                    System.out.println("Invalid request format.\n");
                    getGETHelp();
                }
                break;
            case ALL:


        }
    }



    private void getUserByID(String userId) {
        if (! isLong(userId)) {
            System.out.println(
                    "The user`s id can be the only number like. Please, check the user`s id and try again.");
            return;
        }

        Optional<User> user = this.userController.getUserById(userId);
        if (user.isPresent()) {
            System.out.println(user.get().toString());
        } else {
            System.out.println("The repository does not contain the user with ID: " + userId);
        }
    }

    private void getGETHelp() {
        String helpInfo = "For adding user in repository it can be used two formats of data:\n" +
                "\tVariant 1: [user first name] [user last name] [user region]\n" +
                "\tVariant 2: [user first name] [user last name] [user role] [user region]\n" +
                "For example:\n" + "\t Variant 1: Ivan Ivanov Moscow\n" +
                "\t Variant 2: Ivan Ivanov admin Moscow\n";
        System.out.println(helpInfo);
    }




    private boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
