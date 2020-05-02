package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFasade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Acceptance test - full infractructure stack - without WEB API.
 * Tested is flow via Fasade
 */
@SpringBootTest
@Import(ReservationConfiguration.class)
@AutoConfigureMockMvc
@ComponentScan({"mw.ars.reservations.reservation.infrastructure"})
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
