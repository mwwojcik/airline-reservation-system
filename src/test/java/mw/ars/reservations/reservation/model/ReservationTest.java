package mw.ars.reservations.reservation.model;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.SeatNumber;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

class ReservationTest {

  @DisplayName("Should create new reservation")
  @Test
  void shouldCreateNewReservation() {
    // given
    var anyFlight = FlightId.of(UUID.randomUUID());
    var anyCustomer = CustomerId.of(UUID.randomUUID());
    var oneReservationSoFar=1;
    // when
    var res = InitialReservation.create(oneReservationSoFar,anyFlight,anyCustomer);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
  }


  @DisplayName("Should not create when reservations limit exceeded")
  @Test
  void shouldNotCreateReservationLimitExceeded() {
    // given
    var anyFlight = FlightId.of(UUID.randomUUID());
    var anyCustomer = CustomerId.of(UUID.randomUUID());
    var oneReservationSoFar=15;
    // when
    var res = InitialReservation.create(oneReservationSoFar,anyFlight,anyCustomer);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(false);
  }

  @DisplayName("shouldCreateRegisteredReservation")
  @Test
  void shouldCreateRegisteredReservation() {
   // given 
    var anyInitial = ReservationFixture.initial();
    var anySeat = SeatNumber.of(10);
    var anyDepartureDate=LocalDateTime.now();
    var anyPrice=Money.of(10,"USD");
    // when 
    var res = anyInitial.register(anySeat, anyPrice, anyDepartureDate);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(RegisteredReservation.class);
  }


  @DisplayName("Should hold when number of holded reservations equals 2")
  @Test
  void shouldHoldWhenNumberOfLockedReservationsEquals2() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayFor3Weeks();
    var currentlyHolded = 2;
    // when
    var res = registered.hold(currentlyHolded);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(HoldedReservation.class);
  }

  @DisplayName("Should not hold when number of holded reservations equals 3")
  @Test
  void shouldHoldWhenNumberOfLockedReservationsEquals3() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayFor3Weeks();
    var currentlyHolded = 3;
    // when
    var res = registered.hold(currentlyHolded);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(false);
  }

  @DisplayName("Should hold when departure date more than 2 weeks")
  @Test
  void shouldHoldWhenDepartureDateMoreThan2Weeks() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayFor3Weeks();
    var currentlyHolded = 2;
    // when
    var res = registered.hold(currentlyHolded);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(HoldedReservation.class);
  }

  @DisplayName("Should not hold when departure date less than 2 weeks")
  @Test
  void shouldNotHoldWhenDepartureDateLessThan2Weeks() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayForOneWeek();
    var currentlyHolded = 2;
    // when
    var res = registered.hold(currentlyHolded);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(false);
  }

  @DisplayName("Should confirm when departure date more than 2 weeks")
  @Test
  void shouldConfirmWhenDepartureDateMoreThan2Weeks() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayFor3Weeks();

    // when
    var res = registered.confirm();
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(ConfirmedReservation.class);
  }

  @DisplayName("Should confirm when departure date less than 2 weeks")
  @Test
  void shouldConfirmWhenDepartureDateLessThan2Weeks() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayForOneWeek();

    // when
    var res = registered.confirm();
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(ConfirmedReservation.class);
  }

  @DisplayName("Should reschedule reservation when reschedule first time")
  @Test
  void shouldRescheduleReservationWhenRescheduledFirstTime() {
    // given
    var confirmed = ReservationFixture.confirmed();
    var anySeat = SeatNumber.of(10);
    var anyDepartureDate=LocalDateTime.now();
    var anyPrice=Money.of(10,"USD");
    var anyFlight= FlightId.of(UUID.randomUUID());
    // when
    var res = confirmed.reschedule(1);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(RescheduledReservation.class);
  }

  @DisplayName("Should not reschedule reservation when rescheduled 3 times")
  @Test
  void shouldRescheduleReservationWhenRescheduledThirdTime() {
    // given
    var confirmed = ReservationFixture.confirmed();
    var anySeat = SeatNumber.of(10);
    var anyDepartureDate=LocalDateTime.now();
    var anyPrice=Money.of(10,"USD");
    var anyFlight= FlightId.of(UUID.randomUUID());
    // when
    var res = confirmed.reschedule(3);
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(false);
  }


  @DisplayName("Should cancel reservation when it is holded")
  @Test
  void shouldCancelReservationWhenItIsHolded() {
    // given
    var registered = ReservationFixture.registeredWithDepartureDayFor3Weeks();
    var currentlyHolded = 2;
    var resHold = registered.hold(currentlyHolded);
    var holded=((HoldedReservation)resHold.returned());

    // when
    var res = holded.cancel();
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(CancelledReservation.class);
  }

  @DisplayName("Should cancel reservation when it is confirmed")
  @Test
  void shouldCancelReservationWhenItIsConfirmed() {
    // given
    var confirmed = ReservationFixture.confirmed();

    // when
    var res = confirmed.cancel();
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
    Assertions.assertThat(res.returned()).isExactlyInstanceOf(CancelledReservation.class);
  }
}
