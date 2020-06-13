package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.view.PostsRequestsHandlers.*;

import java.util.List;

public class PostView {

    private RequestHandler requestHandler;

    public PostView() throws ClassNotFoundException {
        this.requestHandler = new HelpPostRequestHandler(new AddPostRequestHandler(
                new ChangePostRequestHandler(
                        new GetPostRequestHandler(new RemovePostRequestHandler()))));
    }

    public void action(String action, List<String> options) throws ClassNotFoundException {
        requestHandler.handleRequest(action, options);
    }


}
