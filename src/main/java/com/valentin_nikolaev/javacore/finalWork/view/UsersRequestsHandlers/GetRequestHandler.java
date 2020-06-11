package com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.controller.UserController;
import com.valentin_nikolaev.javacore.finalWork.models.User;

import java.util.List;
import java.util.Optional;

public class GetRequestHandler extends UserRequestHandler {

    //Parameters of the method to get user`s data
    private final String ID         = "id";
    private final String ALL        = "all";
    private final String FIRST_NAME = "name.first";
    private final String LAST_NAME  = "name.last";
    private final String ROLE       = "role";
    private final String REGION     = "region";

    private UserController userController;

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {
        if (GET.equals(action)) {
            this.userController = new UserController();
            String       requestType    = options.get(0);
            List<String> requestOptions = getOptionsWithOutFirst(options);
            getUserData(requestType, requestOptions);
        } else {
            getNextHandler(action, options);
        }
    }

    private void getUserData(String requestType, List<String> requestOptions) {
        switch (requestType) {
            case HELP:
                getHelpForGettingUserDataRequest();
                break;
            case ID:
                if (requestOptions.size() != 1) {
                    getUserByID(requestOptions.get(0));
                } else {
                    System.out.println("Invalid request format.\n");
                    getHelpForGettingUserDataRequest();
                }
                break;
            case ALL:
                getAllRequest(requestOptions);
                break;
            default:
                System.out.println(
                        "Invalid get request type. Please, check request and try again, or " +
                                "call \"get help\".");
                break;
        }
    }

    private void getUserByID(String userId) {
        if (! isLong(userId)) {
            System.out.println(
                    "The user`s id should consist only of numbers. Please, check the user`s id " +
                            "and try again.");
            return;
        }

        Optional<User> user = this.userController.getUserById(userId);
        if (user.isPresent()) {
            System.out.println(user.get().toString());
        } else {
            System.out.println("The repository does not contain the user with ID: " + userId);
        }
    }

    private void getAllRequest(List<String> options) {
        if (options.size() == 0) {
            getAllUsersList();
        } else {
            getAllRequestWithOptions(options);
        }
    }

    private void getAllUsersList() {
        List<User> users = this.userController.getAllUsersList();
        users.forEach(System.out::println);
    }

    private void getAllRequestWithOptions(List<String> options) {
        if (options.size() == 2) {
            String optionType = options.get(0);
            String option     = options.get(1);
            switch (optionType) {
                case FIRST_NAME:
                    getUsersListWithFirstName(option);
                    break;
                case LAST_NAME:
                    getUsersListWithLastName(option);
                    break;
                case ROLE:
                    getUsersListWithRole(option);
                    break;
                case REGION:
                    getUsersListFromRegion(option);
                    break;
                default:
                    System.out.println(
                            "Invalid option type. Please, check option type and try " + "again.\n");
                    break;
            }
        } else {
            System.out.println(
                    "Invalid request`s options format. Please, check options format " + "and" +
                            " try again.\n");
        }
    }

    private void getUsersListWithFirstName(String name) {
        List<User> users = this.userController.getUsersWithFirstName(name);
        if (users.size() != 0) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No one matches found in repository.\n");
        }
    }

    private void getUsersListWithLastName(String lastName) {
        List<User> users = this.userController.getUsersWithLastName(lastName);
        if (users.size() != 0) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No one matches found in repository.\n");
        }
    }

    private void getUsersListWithRole(String role) {
        List<User> users = this.userController.getUsersWithRole(role);
        if (users.size() != 0) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No one matches found in repository.\n");
        }
    }

    private void getUsersListFromRegion(String region) {
        List<User> users = this.userController.getUsersFrom(region);
        if (users.size() != 0) {
            users.forEach(System.out::println);
        } else {
            System.out.println("No one matches found in repository.\n");
        }
    }

    private void getHelpForGettingUserDataRequest() {
        String helpInfo =
                "For getting user`s data from the repository it can be used next formats of" + " " +
                        "request:\n" + "\t1: id [id number] - return user data\n" +
                        "\t2: all - return list of all users in repository\n" + "\t3: all " +
                        FIRST_NAME + " [user first name] - return list of users with requested " +
                        "user`s " + "first name\n" + "\t4: all " + LAST_NAME +
                        " [user last name] - return list of users with requested user`s" +
                        " last " + "name\n" + "\t5: all " + ROLE +
                        " [role name] - return list of users with requested role\n" + "\t6: all " +
                        REGION + " [region name] - return list of users from requested region\n";

        System.out.println(helpInfo);
    }
}
