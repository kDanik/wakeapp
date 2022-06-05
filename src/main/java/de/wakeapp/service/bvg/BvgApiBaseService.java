package de.wakeapp.service.bvg;

import de.wakeapp.service.BaseApiService;
import org.apache.http.client.utils.URIBuilder;
import de.wakeapp.service.BaseApiService;

public abstract class BvgApiBaseService extends BaseApiService {
    private static final String API_HOST = "v5.bvg.transport.rest";
    private static final String API_SCHEME = "https";

    protected static URIBuilder createBaseApiUriBuilder() {
        return new URIBuilder()
                .setScheme(API_SCHEME)
                .setHost(API_HOST);
    }
}
