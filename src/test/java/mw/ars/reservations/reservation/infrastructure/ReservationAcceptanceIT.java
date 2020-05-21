package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import mw.ars.reservations.reservation.infrastructure.db.ReservationEntity;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.reservations.reservation.infrastructure.testapp.ReservationTestApplication;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Acceptance test - full infractructure stack - without WEB API. Tested is flow via Fasade */
@SpringBootTest(classes = ReservationTestApplication.class)
//@ActiveProfiles("inmemory")
@ActiveProfiles("embedded")
class ReservationAcceptanceIT {

  @Autowired private ReservationFacade reservationFacade;
  @Autowired  ReservationRepositoryDB repoDB;

  //@Autowired private InMemoryReservationRepository repository;

 /* @BeforeEach
  void clearRepository() {
    repository.clearAll();
  }*/

  @DisplayName("test jednego kroku")
  @Test
  void testJednegoKroku() {
    var customerId = CustomerId.of(UUID.randomUUID());
    var flightId = FligtId.of(UUID.randomUUID());
    // given
    var resId = reservationFacade.create(CreateReservationCommand.of(customerId, flightId));
    // when
    var res = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(res.isEmpty()).isFalse();
    Assertions.assertThat(res.get(0).isNew()).isTrue();

  }

  @DisplayName("Should realize main ticket reservation process (create/register/hold/confirm/reschedule/cancel).")
  @Test
  void shouldRealizeMainReservationProcess() {
    var customerId = CustomerId.of(UUID.randomUUID());
    var flightId = FligtId.of(UUID.randomUUID());
    // given
    var resId = reservationFacade.create(CreateReservationCommand.of(customerId, flightId));
    // when
    var result = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(result.isEmpty()).isFalse();
    Assertions.assertThat(result.get(0).isNew()).isTrue();

    Optional<ReservationDTO> res=Optional.empty();
   /* reservationFacade.register(RegistrationCommand.of(resId));
    res = reservationFacade.findByFlightId(FindByFlightIdCommand.of(customerId, flightId));
    Assertions.assertThat(res.isPresent()).isTrue();
    Assertions.assertThat(res.get().isRegistered()).isTrue();*/

    //given
    var withSeat = SeatNumber.of(10);
    var withDepartureDate = LocalDateTime.now().plusDays(30);
    //when
    res=Optional.empty();
    reservationFacade.holdOn(HoldOnReservationCommand.of(resId, withSeat, withDepartureDate));
    res=reservationFacade.findByReservationId(FindByReservationIdCommnad.of(resId));
    //then
    Assertions.assertThat(res.isPresent()).isTrue();
    Assertions.assertThat(res.get().isHolded()).isTrue();

    res=Optional.empty();
    reservationFacade.confirm(ConfirmationCommand.of(resId));
    res=reservationFacade.findByReservationId(FindByReservationIdCommnad.of(resId));
    Assertions.assertThat(res.isPresent()).isTrue();
    Assertions.assertThat(res.get().isConfirmed()).isTrue();

    var newFlightId = FligtId.of(UUID.randomUUID());
    var newSeatId = SeatNumber.of(11);
    var newDepartureTime = LocalDateTime.now().plusDays(15);
    var resheduledId = reservationFacade.reschedule(RescheduleCommand.of(resId, newFlightId, newSeatId, newDepartureTime));

    res=Optional.empty();
    res=reservationFacade.findByReservationId(FindByReservationIdCommnad.of(resId));
    Assertions.assertThat(res.isPresent()).isTrue();
    Assertions.assertThat(res.get().isRescheduled()).isTrue();

    res=Optional.empty();
    res=reservationFacade.findByReservationId(FindByReservationIdCommnad.of(resheduledId));
    Assertions.assertThat(res.isPresent()).isTrue();
    Assertions.assertThat(res.get().isConfirmed()).isTrue();

    reservationFacade.cancel(CancelByResrvationId.of(resheduledId));
    res=reservationFacade.findByReservationId(FindByReservationIdCommnad.of(resheduledId));
    Assertions.assertThat(res.isPresent()).isTrue();
    Assertions.assertThat(res.get().isCancelled()).isTrue();

  }
}
