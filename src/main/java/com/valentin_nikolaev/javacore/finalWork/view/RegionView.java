package com.valentin_nikolaev.javacore.finalWork.view;

import com.valentin_nikolaev.javacore.finalWork.view.RegionRequestsHandlers.AddRegionRequestHandler;

import java.util.List;

public class RegionView {

    private RequestHandler requestHandler;

    public RegionView() throws ClassNotFoundException {
        this.requestHandler = new AddRegionRequestHandler();
    }

    public void action(String action, List<String> options) throws ClassNotFoundException {
        requestHandler.handleRequest(action, options);
    }
}
