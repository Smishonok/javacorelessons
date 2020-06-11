package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.view.PostsRequestsHandlers.*;

import java.util.List;

public class PostView {

    private RequestHandler requestHandler;

    public PostView() throws ClassNotFoundException {
        this.requestHandler = new AddPostRequestHandler().setNextHandler(
                new GetPostRequestHandler()).setNextHandler(new ChangePostRequestHandler())
                                                         .setNextHandler(
                                                                 new RemovePostRequestHandler())
                                                         .setNextHandler(
                                                                 new HelpPostRequestHandler());
    }

    public void action(String action, List<String> options) throws ClassNotFoundException {
        requestHandler.handleRequest(action, options);
    }


}
