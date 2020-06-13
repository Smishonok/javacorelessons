package com.valentin_nikolaev.javacore.finalWork.view.PostsRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.view.RequestHandler;

import java.util.List;

public class RemovePostRequestHandler extends PostRequestHandler {

    public RemovePostRequestHandler(){}

    public RemovePostRequestHandler(RequestHandler nextRequestHandler) {
        super(nextRequestHandler);
    }

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {

    }
}
