package com.valentin_nikolaev.javacore.finalWork.view.RegionRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;

import java.util.List;

public class RemoveRegionRequestHandler extends RegionRequestHandler {

    private RegionController regionController;

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {
        if (REMOVE.equals(action)) {
            this.regionController = new RegionController();

        } else {
            getNextHandler(action,options);
        }
    }







}
