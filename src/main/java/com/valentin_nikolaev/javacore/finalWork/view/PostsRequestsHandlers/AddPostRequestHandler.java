package com.valentin_nikolaev.javacore.finalWork.view.PostsRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.view.RequestHandler;

import java.util.List;

public class AddPostRequestHandler extends PostRequestHandler{

    public AddPostRequestHandler (){}

    public AddPostRequestHandler(RequestHandler nextRequestHandler) {
        super(nextRequestHandler);
    }

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {

    }
}
