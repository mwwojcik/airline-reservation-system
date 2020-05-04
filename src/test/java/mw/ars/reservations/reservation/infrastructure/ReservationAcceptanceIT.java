package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.infrastructure.testapp.ReservationInMemoryTestApplication;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/** Acceptance test - full infractructure stack - without WEB API. Tested is flow via Fasade */
@SpringBootTest(classes = ReservationInMemoryTestApplication.class)
class ReservationAcceptanceIT {

  @Autowired private ReservationFacade reservationFacade;


}
