package de.wakeapp;

import de.wakeapp.service.bvg.journey.BvgJourneyService;
import de.wakeapp.service.bvg.location.BvgLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BvgJourneyServiceTest {
    static BvgLocation validLocation1;
    static BvgLocation validLocation2;
    static BvgLocation invalidLocation;

    @BeforeAll
    static void setup() {
        validLocation1 = new BvgLocation("900000100003","S+U Alexanderplatz");
        validLocation2 = new BvgLocation("12049 Berlin-Neukölln, Hermannstr. 23", "52.483034", "13.424814");
        invalidLocation = new BvgLocation("very wrong id","test");
    }

    /*
        This test can only test if BvgJourneyService delivers result with given parameters, no the correctness of it, because it is dynamic
     */
    @Test
    void journeyTest() {
        Assertions.assertNotNull(BvgJourneyService.getJourneyInformation(validLocation1, validLocation2, LocalDateTime.now(), true, true, false));
        Assertions.assertNotNull(BvgJourneyService.getJourneyInformation(validLocation2, validLocation1, LocalDateTime.now(), true, true, true));

        Assertions.assertNull(BvgJourneyService.getJourneyInformation(invalidLocation, validLocation2, LocalDateTime.now(), true, true, true));
        Assertions.assertNull(BvgJourneyService.getJourneyInformation(validLocation1, invalidLocation, LocalDateTime.now(), false, true, false));
    }
}
