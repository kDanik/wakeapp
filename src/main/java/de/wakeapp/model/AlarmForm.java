package de.wakeapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "alarmForm")
@Getter
@Setter
public class AlarmForm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer id;

    @Column(name = "arrivalTime")
    private String arrivalTime;
    @Column(name = "timeToGetReady")
    private Integer timeToGetReady;
    @Column(name = "addressResidence")
    private String addressResidence;
    @Column(name = "addressDestination")
    private String addressDestination;
    @Column(name = "name")
    private String name;

    @Column(name = "useBus")
    private Boolean useBus;
    @Column(name = "useTram")
    private Boolean useTram;
    @Column(name = "useSAndUBahn")
    private Boolean useSAndUBahn;
}
