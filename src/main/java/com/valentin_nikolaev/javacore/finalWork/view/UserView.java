package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.AddRequestHandler;
import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.GetRequestHandler;
import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.HelpRequestHandler;
import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.RemoveRequestsHandler;

import java.util.List;

public class UserView {

    private RequestHandler requestHandler;

    public UserView() throws ClassNotFoundException {
        this.requestHandler = new HelpRequestHandler().setNextHandler(new AddRequestHandler())
                                                      .setNextHandler(new GetRequestHandler())
                                                      .setNextHandler(new RemoveRequestsHandler());
    }

    public void action(String action, List<String> options) throws ClassNotFoundException {
        requestHandler.handleRequest(action, options);
    }
}
