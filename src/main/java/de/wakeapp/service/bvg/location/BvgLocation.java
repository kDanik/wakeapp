package de.wakeapp.service.bvg.location;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BvgLocation {
    public static final String STATION = "Station";
    public static final String POINT_OF_INTEREST = "PointOfInterest";
    public static final String ADDRESS = "Address";


    private final String type;

    private String id;

    private String name;
    private String address;
    private String longitude;
    private String latitude;

    public BvgLocation(String id, String name) {
        this.type = STATION;
        this.name = name;
        this.id = id;
    }

    public BvgLocation(String id, String name, String latitude, String longitude) {
        this.type = POINT_OF_INTEREST;
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BvgLocation(String address, String latitude, String longitude) {
        this.type = ADDRESS;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
