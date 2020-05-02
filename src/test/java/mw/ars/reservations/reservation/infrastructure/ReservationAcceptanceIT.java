package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFasade;
import mw.ars.reservations.reservation.infrastructure.testapp.ReservationInMemoryTestApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Acceptance test - full infractructure stack - without WEB API.
 * Tested is flow via Fasade
 */
@SpringBootTest(classes = ReservationInMemoryTestApplication.class)
@AutoConfigureMockMvc
class ReservationAcceptanceIT {

    @Autowired
    private ReservationFasade reservationFasade;

    @DisplayName("Simple integration test")
    @Test
    void simpleIntegrationTest() {
    // given
    // when
    // then
    System.out.println("test");
//        Fail.fail("Write your test");
     }

}
