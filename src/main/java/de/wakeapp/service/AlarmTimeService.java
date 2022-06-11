package de.wakeapp.service;

import de.wakeapp.bean.AlarmTimeFormBean;
import de.wakeapp.service.bvg.journey.BvgJourneyService;
import de.wakeapp.service.bvg.location.BvgLocation;
import de.wakeapp.service.bvg.location.BvgLocationQueryService;
import de.wakeapp.service.localDateTime.LocalDateTimeConverter;
import de.wakeapp.service.weather.WeatherForecastServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlarmTimeService {
    private final WeatherForecastServiceWrapper weatherForecastServiceWrapper;

    private final LocalDateTimeConverter localDateTimeConverter;

    @Autowired
    public AlarmTimeService(WeatherForecastServiceWrapper weatherForecastServiceWrapper, LocalDateTimeConverter localDateTimeConverter) {
        this.weatherForecastServiceWrapper = weatherForecastServiceWrapper;
        this.localDateTimeConverter = localDateTimeConverter;
    }

    public LocalDateTime calculateAlarmTime(AlarmTimeFormBean alarmTimeFormBean) {
        LocalDateTime calculatedAlarmTime = localDateTimeConverter.convertArrivalTimeToDateTime(alarmTimeFormBean.getArrivalTime());

        calculatedAlarmTime = addTravelTime(calculatedAlarmTime, alarmTimeFormBean);

        calculatedAlarmTime = addTimeDueToWeatherConditions(calculatedAlarmTime);

        calculatedAlarmTime = addTimeForPreparations(calculatedAlarmTime, alarmTimeFormBean);

        return calculatedAlarmTime;
    }

    private LocalDateTime addTravelTime(LocalDateTime calculatedAlarmTime, AlarmTimeFormBean alarmTimeFormBean) {
        BvgLocation from = BvgLocationQueryService.getLocationInfoForQuery(alarmTimeFormBean.getAddressResidence());
        BvgLocation to = BvgLocationQueryService.getLocationInfoForQuery(alarmTimeFormBean.getAddressDestination());

        Boolean useBus = alarmTimeFormBean.getUseBus();
        Boolean useTram = alarmTimeFormBean.getUseTram();
        Boolean useSAndUBahn = alarmTimeFormBean.getUseSAndUBahn();

        return BvgJourneyService.getJourneyDepartureTime(from, to, calculatedAlarmTime, useBus, useSAndUBahn, useTram);
    }

    private LocalDateTime addTimeForPreparations(LocalDateTime calculatedAlarmTime, AlarmTimeFormBean alarmTimeFormBean) {
        return calculatedAlarmTime.minusMinutes(alarmTimeFormBean.getTimeToGetReady());
    }

    private LocalDateTime addTimeDueToWeatherConditions(LocalDateTime calculatedAlarmTime) {
        if (weatherForecastServiceWrapper.willBeSnowyOrRainyWeather(calculatedAlarmTime)) {
            return calculatedAlarmTime.minusMinutes(10);
        }
        return calculatedAlarmTime;
    }

}
