package com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;
import com.valentin_nikolaev.javacore.finalWork.models.Role;
import com.valentin_nikolaev.javacore.finalWork.view.RequestHandler;

import java.util.List;

public abstract class UserRequestHandler extends RequestHandler {

    protected final String ADD    = "add";
    protected final String GET    = "get";
    protected final String CHANGE = "change";
    protected final String REMOVE = "remove";
    protected final String HELP   = "help";


    @Override
    public void getHelp() {
        String helpInfo = "Invalid request type, please check request type and try again.\n" +
                "Help information:\n" +
                "This is the part of the console app in which you can add, change and " +
                "remove user data from repository. The main commands are:\n" +
                "\tadd - adding new user;\n" + "\tget - getting user data from repository;\n" +
                "\tchange - changing user data in repository\n" +
                "\tremove - removing user from repository;\n" +
                "\n\tCalling \"help\" after each of commands calls the help`s information for the" +
                " corresponding command.";
        System.out.println(helpInfo);
    }


}