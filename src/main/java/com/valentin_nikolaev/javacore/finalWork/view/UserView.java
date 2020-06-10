package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;
import com.valentin_nikolaev.javacore.finalWork.controller.UserController;
import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.AddRequestHandler;
import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.GetRequestHandler;
import com.valentin_nikolaev.javacore.finalWork.view.UsersRequestsHandlers.HelpRequestHandler;

import java.util.List;

public class UserView {

    private UserController   userController;
    private RegionController regionController;
    private RequestHandler   requestHandler;

    public UserView() throws ClassNotFoundException {
        this.userController   = new UserController();
        this.regionController = new RegionController();
        this.requestHandler   = new HelpRequestHandler().setNextHandler(new AddRequestHandler())
                                                        .setNextHandler(new GetRequestHandler());
    }

    public void action(String action, List<String> options) throws ClassNotFoundException {
        requestHandler.handleRequest(action,options);
    }
}
