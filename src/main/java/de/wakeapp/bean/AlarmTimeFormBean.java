package de.wakeapp.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlarmTimeFormBean {
    public String arriveBy;
    public Integer timeToGetReady;

    public String addressResidence;
    public String addressDestination;

    public Boolean useBus;
    public Boolean useTram;
    public Boolean useSAndUBahn;

    public String calculatedAlarmTime;
}
