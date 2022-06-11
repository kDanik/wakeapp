package de.wakeapp.service.bvg.location;

import de.wakeapp.service.bvg.BvgApiBaseService;
import org.apache.http.client.utils.URIBuilder;

public class BvgLocationQueryService extends BvgApiBaseService {
    private static final String REQUEST_PATH = "locations";
    private static final int RESULTS_TO_FETCH = 1;


    public static BvgLocation getLocationInfoForQuery(String query) {
        String url = buildRequestUrl(query);
        String result = executeGetRequest(url);
        return BvgLocationFactory.createFromJsonString(result);
    }

    private static String buildRequestUrl(String query) {
        URIBuilder uriBuilder = createBaseApiUriBuilder();

        uriBuilder.setPath(REQUEST_PATH)
                .addParameter("query", query)
                .addParameter("results", String.valueOf(RESULTS_TO_FETCH));

        return uriBuilder.toString();
    }
}
