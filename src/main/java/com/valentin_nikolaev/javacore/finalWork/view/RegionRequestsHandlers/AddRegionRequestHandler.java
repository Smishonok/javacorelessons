package com.valentin_nikolaev.javacore.finalWork.view.RegionRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;

import java.util.List;

public class AddRegionRequestHandler extends RegionRequestHandler {

    private RegionController regionController;

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {
        if (ADD.equals(action)) {
            this.regionController = new RegionController();
            addRegion(options);
        } else {
            getNextHandler(action,options);
        }
    }

    private void addRegion(List<String> options) {
        if (options.size() == 1) {
            this.regionController.addRegion(options.get(0));
        } else {
            System.out.println("Invalid request format. Please, check request format and try " +
                                       "again, or get help information.");
        }
    }
}
