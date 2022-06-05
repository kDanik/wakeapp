package de.wakeapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import de.wakeapp.service.bvg.BvgLocation;
import de.wakeapp.service.bvg.location.BvgLocationQueryService;

public class BvgLocationQueryServiceTest {

    /*
        BvgLocation of type Address should have address, lan, lon attributes and no id or name
     */
    @Test
    void queryAddress() {
        String query = "Pankstraße 47 13357";

        BvgLocation test = BvgLocationQueryService.getLocationInfoForQuery(query);

        Assertions.assertTrue(test.getType().equals(BvgLocation.ADDRESS));
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

        Assertions.assertTrue(test.getType().equals(BvgLocation.POINT_OF_INTEREST));
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

        Assertions.assertTrue(test.getType().equals(BvgLocation.STATION));
        Assertions.assertNotNull(test.getId());
        Assertions.assertNotNull(test.getName());

        Assertions.assertNull(test.getLatitude());
        Assertions.assertNull(test.getLongitude());
        Assertions.assertNull(test.getAddress());
    }
}
