package de.wakeapp.service.weather;

import org.apache.http.client.utils.URIBuilder;
import de.wakeapp.service.BaseApiService;
import de.wakeapp.service.properties.PropertiesNotFoundException;
import de.wakeapp.service.properties.PropertiesReader;

import java.util.Properties;

public abstract class WeatherApiBaseService extends BaseApiService {
    private static final String API_HOST = "api.openweathermap.org";
    private static final String API_SCHEME = "https";

    protected final String API_KEY;

    public WeatherApiBaseService() throws PropertiesNotFoundException {
        Properties properties = PropertiesReader.readAll(PropertiesReader.API_PROPERTIES_NAME);
        this.API_KEY = properties.getProperty("api.key.openweathermap");
    }

    protected static URIBuilder createBaseApiUriBuilder() {
        return new URIBuilder()
                .setScheme(API_SCHEME)
                .setHost(API_HOST);
    }
}
