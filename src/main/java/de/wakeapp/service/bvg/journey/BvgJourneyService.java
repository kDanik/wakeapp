package de.wakeapp.service.bvg.journey;

import org.apache.http.client.utils.URIBuilder;
import de.wakeapp.service.bvg.BvgApiBaseService;
import de.wakeapp.service.bvg.BvgLocation;

import java.time.LocalDateTime;

public class BvgJourneyService extends BvgApiBaseService {
    private static final String REQUEST_PATH = "journeys";
    private static final int RESULTS_TO_FETCH = 1;


    public static String getJourneyInformation(BvgLocation from, BvgLocation to, LocalDateTime arrivalBy) {
        String url = buildRequestUrl(from, to, arrivalBy);
        return executeGetRequest(url);
    }

    private static String buildRequestUrl(BvgLocation from, BvgLocation to, LocalDateTime arrivalBy) {
        URIBuilder uriBuilder = createBaseApiUriBuilder();

        uriBuilder.setPath(REQUEST_PATH)
                .addParameter("arrival", arrivalBy.toString())
                .addParameter("results", String.valueOf(RESULTS_TO_FETCH));

        if (from.getType().equals(BvgLocation.POINT_OF_INTEREST)) {
            uriBuilder.addParameter("from.id", String.valueOf(from.getId()));
            uriBuilder.addParameter("from.latitude", from.getLatitude());
            uriBuilder.addParameter("from.longitude", from.getLongitude());
            uriBuilder.addParameter("from.name", from.getName());

        } else if (from.getType().equals(BvgLocation.ADDRESS)) {
            uriBuilder.addParameter("from.latitude", from.getLatitude());
            uriBuilder.addParameter("from.longitude", from.getLongitude());
            uriBuilder.addParameter("from.address", from.getAddress());

        } else if (from.getType().equals(BvgLocation.STATION)) {
            uriBuilder.addParameter("from", String.valueOf(from.getId()));
        }


        if (to.getType().equals(BvgLocation.POINT_OF_INTEREST)) {
            uriBuilder.addParameter("to.id", to.getId());
            uriBuilder.addParameter("to.latitude", to.getLatitude());
            uriBuilder.addParameter("to.longitude", to.getLongitude());
            uriBuilder.addParameter("to.name", to.getName());

        } else if (to.getType().equals(BvgLocation.ADDRESS)) {
            uriBuilder.addParameter("to.latitude", to.getLatitude());
            uriBuilder.addParameter("to.longitude", to.getLongitude());
            uriBuilder.addParameter("to.address", to.getAddress());

        } else if (to.getType().equals(BvgLocation.STATION)) {
            uriBuilder.addParameter("to", to.getId());
        }

        return uriBuilder.toString();
    }
}
