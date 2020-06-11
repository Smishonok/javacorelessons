package com.valentin_nikolaev.javacore.finalWork.view.RegionRequestsHandlers;

import java.util.List;

public class HelpRegionRequestHandler extends RegionRequestHandler {

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {
        if (HELP.equals(action)) {
            String helpInfo =
                    "This is the part of the console app in which you can add, change and " +
                            "remove user data from repository. The main commands are:\n" +
                            "\tadd - adding new region;\n" +
                            "\tget - getting region data from repository;\n" +
                            "\tchange - changing region data in repository;\n" +
                            "\tremove - removing region from repository;\n" +
                            "\n\tCalling \"help\" after each of commands calls the help`s information for the" +
                            " corresponding command.";
            System.out.println(helpInfo);
        } else {
            getNextHandler(action, options);
        }
    }
}