package de.wakeapp.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlarmTimeFormBean {
    private String arrivalTime;
    private Integer timeToGetReady = 0;

    private String addressResidence;
    private String addressDestination;

    private Boolean useBus = true;
    private Boolean useTram = true;
    private Boolean useSAndUBahn = true;

    private String calculatedAlarmTime;
}
