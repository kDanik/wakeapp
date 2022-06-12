package de.wakeapp;

import de.wakeapp.service.bvg.location.BvgLocation;
import de.wakeapp.service.bvg.location.BvgLocationQueryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BvgLocationQueryServiceTest {

    /*
        BvgLocation of type Address should have address, lan, lon attributes and no id or name
     */
    @Test
    void queryAddress() {
        String query = "Pankstraße 47 13357";

        BvgLocation test = BvgLocationQueryService.getLocationInfoForQuery(query);

        Assertions.assertEquals(BvgLocation.ADDRESS, test.getType());
        Assertions.assertNotNull(test.getLatitude());
        Assertions.assertNotNull(test.getLongitude());
        Assertions.assertNotNull(test.getAddress());

        Assertions.assertNull(test.getName());
        Assertions.assertNull(test.getId());
    }

    /*
        BvgLocation of type POI(Point of interest) should have name, id, lan, lon attributes and no address
    */
    @Test
    void queryPointOfInterest() {
        String query = "ATZE Musiktheater";

        BvgLocation test = BvgLocationQueryService.getLocationInfoForQuery(query);

        Assertions.assertEquals(BvgLocation.POINT_OF_INTEREST, test.getType());
        Assertions.assertNotNull(test.getId());
        Assertions.assertNotNull(test.getName());
        Assertions.assertNotNull(test.getLatitude());
        Assertions.assertNotNull(test.getLongitude());

        Assertions.assertNull(test.getAddress());
    }

    /*
        BvgLocation of type Station should have name, id and no other attributes
    */
    @Test
    void queryStation() {
        String query = "alexanderplatz";

        BvgLocation test = BvgLocationQueryService.getLocationInfoForQuery(query);

        Assertions.assertEquals(BvgLocation.STATION, test.getType());
        Assertions.assertNotNull(test.getId());
        Assertions.assertNotNull(test.getName());

        Assertions.assertNull(test.getLatitude());
        Assertions.assertNull(test.getLongitude());
        Assertions.assertNull(test.getAddress());
    }
}
