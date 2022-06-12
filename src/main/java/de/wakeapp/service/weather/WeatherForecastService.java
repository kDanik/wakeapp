package de.wakeapp.service.weather;

import de.wakeapp.service.properties.PropertiesNotFoundException;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class WeatherForecastService extends WeatherApiBaseService {
    private static final String REQUEST_PATH = "data/2.5/forecast";
    private static final String RESPONSE_UNITS = "metric";

    public WeatherForecastService() throws PropertiesNotFoundException {
        super();
    }

    public JSONObject getWeatherForecast(String latitude, String longitude) {
        String url = buildRequestUrl(latitude, longitude);
        return new JSONObject(executeGetRequest(url));
    }

    private String buildRequestUrl(String latitude, String longitude) {
        URIBuilder uriBuilder = createBaseApiUriBuilder();

        uriBuilder.setPath(REQUEST_PATH)
                .addParameter("lat", latitude)
                .addParameter("lon", longitude)
                .addParameter("appid", API_KEY)
                .addParameter("units", RESPONSE_UNITS);

        return uriBuilder.toString();
    }
}
