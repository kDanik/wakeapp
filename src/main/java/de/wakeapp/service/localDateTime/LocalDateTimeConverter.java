package de.wakeapp.service.localDateTime;

import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class LocalDateTimeConverter {
    public LocalDateTime convertArrivalTimeToDateTime(String arrivalTime) {
        LocalTime arrivalTimeConverted = LocalTime.parse(arrivalTime);
        LocalTime currentTime = LocalTime.now();

        LocalDateTime arrivalDateTime = LocalDateTime.now();

        if (currentTime.isAfter(arrivalTimeConverted)) {
            // if current time is past arrival time, calculation should be made for next day
            arrivalDateTime = arrivalDateTime.plusDays(1);
        }
        // if current time is before arrival time, calculation should be made for current day

        arrivalDateTime = arrivalDateTime.withHour(arrivalTimeConverted.getHour());
        arrivalDateTime = arrivalDateTime.withMinute(arrivalTimeConverted.getMinute());
        arrivalDateTime = arrivalDateTime.withSecond(0);
        arrivalDateTime = arrivalDateTime.withNano(0);

        return arrivalDateTime;
    }

    public LocalDateTime utcToCet(LocalDateTime timeInUtc) {
        ZonedDateTime utcTimeZoned = ZonedDateTime.of(timeInUtc, ZoneId.of("UTC"));
        return utcTimeZoned.withZoneSameInstant(ZoneId.of("Europe/Berlin")).toLocalDateTime();
    }

    public LocalDateTime cetToUtc(LocalDateTime timeInCet) {
        ZonedDateTime cetTimeZoned = ZonedDateTime.of(timeInCet, ZoneId.of("Europe/Berlin"));
        return cetTimeZoned.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
}
