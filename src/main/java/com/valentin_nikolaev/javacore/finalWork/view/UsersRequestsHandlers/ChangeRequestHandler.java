package com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers;

import java.util.List;

public class ChangeRequestHandler extends UserRequestHandler {

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {
        if (CHANGE.equals(action)) {

        } else {
            getNextHandler(action, options);
        }
    }
}
