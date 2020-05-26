package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.reservations.reservation.infrastructure.testapp.ReservationTestApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

/** Acceptance test - full infractructure stack - without WEB API. Tested is flow via Fasade */
@SpringBootTest(classes = ReservationTestApplication.class)
// @ActiveProfiles("inmemory")
@ActiveProfiles("embedded")
class ReservationAcceptanceIT {

  @Autowired ReservationRepositoryDB repoDB;
  @Autowired private ReservationFacade reservationFacade;

  @DisplayName(
      "Should realize main ticket reservation process (create/register/hold/confirm/reschedule/cancel).")
  @Test
  void shouldRealizeMainReservationProcess() {
    var customerId = CustomerId.of(UUID.randomUUID());
    var flightId = FlightId.of(UUID.randomUUID());
    // given
    var resId = reservationFacade.create(CreateReservationCommand.of(customerId, flightId));
    // when
    var result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isNew()).isTrue();

    var withSeat = SeatNumber.of(10);
    reservationFacade.register(RegistrationCommand.of(resId, withSeat));
    result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isRegistered()).isTrue();

    reservationFacade.holdOn(HoldOnReservationCommand.of(resId));
    result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isHolded()).isTrue();


    reservationFacade.confirm(ConfirmationCommand.of(resId));
    result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isConfirmed()).isTrue();


    var newFlightId = FlightId.of(UUID.randomUUID());
    var newSeatId = SeatNumber.of(11);
    var newDepartureTime = LocalDateTime.now().plusDays(15);
    var newConfirmedAfterRescheduling =
            reservationFacade.reschedule(
                    RescheduleCommand.of(resId,customerId, newFlightId, newSeatId, newDepartureTime));

    result.clear();
    result=reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, flightId)));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isRescheduled()).isTrue();

    result.clear();
    result=reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, newFlightId)));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isConfirmed()).isTrue();
    Assertions.assertThat(result.get(0).getReservationId().equals(newConfirmedAfterRescheduling));


    /*



    reservationFacade.cancel(CancelByResrvationId.of(resheduledId));
    res = reservationFacade.findByReservationId(FindByReservationIdCommnad.of(resheduledId));
    Assertions.assertThat(res.isPresent()).isTrue();
    Assertions.assertThat(res.get().isCancelled()).isTrue();*/
  }
}
