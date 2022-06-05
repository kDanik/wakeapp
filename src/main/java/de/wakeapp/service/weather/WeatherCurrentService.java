package de.wakeapp.service.weather;

import org.apache.http.client.utils.URIBuilder;
import de.wakeapp.service.properties.PropertiesNotFoundException;

public class WeatherCurrentService extends WeatherApiBaseService {
    private static final String REQUEST_PATH = "data/2.5/weather";
    private static final String RESPONSE_UNITS = "metric";

    public WeatherCurrentService() throws PropertiesNotFoundException {
        super();
    }

    public String getWeatherForecastBerlin() {
        return getWeatherForecast("52.5200", "13.4050");
    }

    public String getWeatherForecast(String latitude, String longitude) {
        String url = buildRequestUrl(latitude, longitude);
        return executeGetRequest(url);
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
