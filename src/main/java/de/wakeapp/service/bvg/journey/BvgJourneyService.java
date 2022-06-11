package de.wakeapp.service.bvg.journey;

import de.wakeapp.service.bvg.BvgApiBaseService;
import de.wakeapp.service.bvg.location.BvgLocation;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BvgJourneyService extends BvgApiBaseService {
    private static final String REQUEST_PATH = "journeys";
    private static final int RESULTS_TO_FETCH = 1;


    public static LocalDateTime getJourneyDepartureTime(BvgLocation from, BvgLocation to, LocalDateTime arrivalBy, boolean useBus, boolean useSAndUBahn, boolean useTram) {
        JSONObject responseObject = getJourneyInformation(from, to, arrivalBy, useBus, useSAndUBahn, useTram);

        // get first proposed journey option (only one is queried)
        JSONObject journey = responseObject.getJSONArray("journeys").getJSONObject(0);

        // get journeyParts (steps) array from first proposed journey option
        JSONArray journeyParts = journey.getJSONArray("legs");

        // get departure time from first journeyPart
        JSONObject initial = journeyParts.getJSONObject(0);
        LocalDateTime departureTime = LocalDateTime.parse(initial.getString("departure"), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        return departureTime;
    }

    public static JSONObject getJourneyInformation(BvgLocation from, BvgLocation to, LocalDateTime arrivalBy, boolean useBus, boolean useSAndUBahn, boolean useTram) {
        String url = buildRequestUrl(from, to, arrivalBy, useBus, useSAndUBahn, useTram);
        String response = executeGetRequest(url);
        return response != null ? new JSONObject(response) : null;
    }

    private static String buildRequestUrl(BvgLocation from, BvgLocation to, LocalDateTime arrivalBy, boolean useBus, boolean useSAndUBahn, boolean useTram) {
        URIBuilder uriBuilder = createBaseApiUriBuilder();

        uriBuilder.setPath(REQUEST_PATH)
                .addParameter("arrival", arrivalBy.toString())
                .addParameter("results", String.valueOf(RESULTS_TO_FETCH))
                .addParameter("suburban", String.valueOf(useSAndUBahn))
                .addParameter("subway", String.valueOf(useSAndUBahn))
                .addParameter("bus", String.valueOf(useBus))
                .addParameter("tram", String.valueOf(useTram));

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
