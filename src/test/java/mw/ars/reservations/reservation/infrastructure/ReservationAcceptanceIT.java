package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.reservations.reservation.infrastructure.testapp.LocalMongoDBTestConfiguration;
import mw.ars.reservations.reservation.infrastructure.testapp.LocalMongoDbTestApplication;
import mw.ars.reservations.reservation.infrastructure.testapp.SimplifiedTestConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

/** Acceptance test - full infractructure stack - without WEB API. Tested is flow via Fasade */
@SpringBootTest(classes = {LocalMongoDbTestApplication.class})
class ReservationAcceptanceIT {

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
    reservationFacade.register(
        RegistrationCommand.of(resId, flightId, LocalDateTime.now().plusDays(30), withSeat));
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
            RescheduleCommand.of(resId, customerId, newFlightId, newSeatId, newDepartureTime));

    result.clear();
    result = reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, flightId)));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isRescheduled()).isTrue();

    result.clear();
    result = reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, newFlightId)));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isConfirmed()).isTrue();
    Assertions.assertThat(result.get(0).getReservationId().equals(newConfirmedAfterRescheduling));

    result.clear();
    result = reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, newFlightId)));
    reservationFacade.cancel(CancelByResrvationId.of(result.get(0).getReservationId()));
    var cancelled =
        reservationFacade.findByFlightId((FindByFlightIdCommand.of(customerId, newFlightId)));
    Assertions.assertThat(cancelled.isEmpty()).isFalse();
    Assertions.assertThat(cancelled.get(0).isCancelled()).isTrue();
  }
}
