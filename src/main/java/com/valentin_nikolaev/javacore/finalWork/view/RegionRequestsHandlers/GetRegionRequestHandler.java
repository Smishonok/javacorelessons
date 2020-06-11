package com.valentin_nikolaev.javacore.finalWork.view.RegionRequestsHandlers;

import com.valentin_nikolaev.javacore.finalWork.controller.RegionController;
import com.valentin_nikolaev.javacore.finalWork.models.Region;

import java.util.List;

public class GetRegionRequestHandler extends RegionRequestHandler {

    private RegionController regionController;

    @Override
    public void handleRequest(String action, List<String> options) throws ClassNotFoundException {
        if (GET.equals(action)) {
            this.regionController = new RegionController();

            processRequest(options);
        } else {
            getNextHandler(action,options);
        }
    }

    private void processRequest(List<String> options) {
        String requestType = "";
        if (options.size() != 0) {
            requestType = options.get(0);
        }

        List<String> requestOptions = getOptionsWithOutFirst(options);

        switch (requestType) {
            case ID:
                getRegionByID(requestOptions);
                break;
            case NAME:

                break;
            case ALL:
                getRegionsList();
                break;
            default:

                break;
        }
    }

    private void getRegionByID(List<String> requestOptions) {

    }

    private void getRegionByName(List<String> requestOptions) {

    }

    private void getRegionsList() {
        List<Region> regions = this.regionController.getAllRegions();
        if (regions.size() != 0) {
            regions.forEach(System.out::println);
        } else {
            System.out.println("Regions list in the repository is empty.\n");
        }
    }


}
