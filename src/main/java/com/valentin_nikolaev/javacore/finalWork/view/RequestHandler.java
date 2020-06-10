package com.valentin_nikolaev.javacore.finalWork.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class RequestHandler {

    private RequestHandler nextRequestHandler;

    public abstract void handleRequest(String action, List<String> options)
            throws ClassNotFoundException;

    public RequestHandler setNextHandler(RequestHandler handler) {
        this.nextRequestHandler = handler;
        return handler;
    }

    public boolean hasNextHandler() {
        boolean hasNext = false;
        if (nextRequestHandler != null) {
            hasNext = true;
        }
        return hasNext;
    }

    public void getNextHandler(String action, List<String> options) throws ClassNotFoundException {
        if (hasNextHandler()) {
            nextRequestHandler.handleRequest(action, options);
        } else {
            getHelp();
        }
    }

    public abstract void getHelp();

    public List<String> getOptionsWithOutFirst(List<String> options) {
        List<String> optionsListWithOutFirst = new ArrayList<>();
        if (options.size() > 1) {
            for (int i = 1; i < options.size(); i++) {
                optionsListWithOutFirst.add(options.get(i));
            }
        }
        return optionsListWithOutFirst;
    }

}
