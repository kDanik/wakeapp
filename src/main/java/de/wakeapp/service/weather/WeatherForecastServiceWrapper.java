package de.wakeapp.service.weather;

import de.wakeapp.service.localDateTime.LocalDateTimeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
public class WeatherForecastServiceWrapper {
    private final WeatherForecastService weatherForecastService;

    private final LocalDateTimeConverter localDateTimeConverter;

    @Autowired
    public WeatherForecastServiceWrapper(WeatherForecastService weatherForecastService, LocalDateTimeConverter localDateTimeConverter) {
        this.weatherForecastService = weatherForecastService;
        this.localDateTimeConverter = localDateTimeConverter;
    }

    public JSONObject getWeatherForecastBerlin() {
        return weatherForecastService.getWeatherForecast("52.5200", "13.4050");
    }

    public boolean willBeSnowyOrRainyWeather(LocalDateTime whenCET) {
        JSONArray weatherForecastList = getWeatherForecastBerlin().getJSONArray("list");

        JSONObject closestForecast = findClosestForecast(whenCET, weatherForecastList);

        Set weatherConditions = getWeatherConditions(closestForecast);

        // TODO intensity of weather condition matters more than just presence of condition! Slight rain or slight snow doesnt effect travel time so much.
        return weatherConditions.contains("Rain") || weatherConditions.contains("Snow") || weatherConditions.contains("Thunder");
    }

    private JSONObject findClosestForecast(LocalDateTime localDateTimeCET, JSONArray weatherForecastList) {
        long intervalBetweenItemsInSeconds = 10800;
        long whenEpochUnixTimestamp = localDateTimeConverter.cetToUtc(localDateTimeCET).toEpochSecond(ZoneOffset.UTC);

        for (int i = 0; i < weatherForecastList.length(); i++) {
            JSONObject weatherForecast = weatherForecastList.getJSONObject(i);

            if ((whenEpochUnixTimestamp - weatherForecast.getLong("dt") + (intervalBetweenItemsInSeconds / 2)) > 0) {
                return weatherForecast;
            }
        }
        // TODO: throw exception instead of returning null
        return null;
    }

    private Set getWeatherConditions(JSONObject forecast) {
        Set<String> weatherConditions = new HashSet<>();

        JSONArray weatherArray = forecast.getJSONArray("weather");

        for (int i = 0; i < weatherArray.length(); i++) {
            JSONObject weatherEntry = weatherArray.getJSONObject(i);
            weatherConditions.add(weatherEntry.getString("main"));
        }
        return weatherConditions;
    }
}
