package com.valentin_nikolaev.javacore.finalWork.view;

public abstract class RequestHandler {

    private RequestHandler nextRequestHandler;

    public abstract void handleRequest(String action, String[] options)
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

    public void getNextHandler(String action,String[] options) throws ClassNotFoundException {
        if (hasNextHandler()) {
            nextRequestHandler.handleRequest(action,options);
        } else {
            getHelp();
        }
    }

    public abstract void getHelp();

}
