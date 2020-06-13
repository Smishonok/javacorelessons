package com.valentin_nikolaev.javacore.finalWork.view.PostsRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.view.RequestHandler;

import java.util.List;

public class ChangePostRequestHandler extends PostRequestHandler {

    public ChangePostRequestHandler(){}

    public ChangePostRequestHandler(RequestHandler nextRequestHandler) {
        super(nextRequestHandler);
    }

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {

    }
}
