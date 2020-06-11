package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.*;

import java.util.List;

public class UserView {

    private RequestHandler requestHandler;

    public UserView() throws ClassNotFoundException {
        this.requestHandler = new HelpUserRequestHandler().setNextHandler(new AddUserRequestHandler())
                                                          .setNextHandler(new GetUserRequestHandler())
                                                          .setNextHandler(new RemoveUserRequestsHandler())
                                                          .setNextHandler(new ChangeUserRequestHandler());
    }

    public void action(String action, List<String> options) throws ClassNotFoundException {
        requestHandler.handleRequest(action, options);
    }
}
