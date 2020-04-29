package mw.ars.reservations.reservation.model;

import mw.ars.commons.model.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

  @DisplayName("Should create new reservation")
  @Test
  void shouldCreateNewReservation() {
    // given
    var reservation = ReservationFixture.simple();
    // when
    var res = reservation.activate();
    // then
    Assertions.assertThat(res.isSuccess()).isEqualTo(true);
  }

  @DisplayName("Should not create when reservations limit exceeded")
  @Test
  void shouldNotCreateReservationLimitExceeded() {
    // given
    var reservation = ReservationFixture.withMaxReservationPerMonth();
    // when
    var res = reservation.activate();
    // then
    Assertions.assertThat(res.isFailure()).isEqualTo(true);
    // Fail.fail("Write your test");
  }

  @DisplayName("Should lock reservation when it is active")
  @Test
  void shouldLockActiveReservation() {
    // given
    var reservation = ReservationFixture.departureDateMoreThanTwoWeeks();
    // when
    var result = reservation.lock();
    // then
    Assertions.assertThat(result.isSuccess()).isEqualTo(true);
  }

  @DisplayName("Should not lock when reservation is confirmed")
  @Test
  void shouldNotLockConfirmedReservation() {
    // given
    var reservation = ReservationFixture.inConfirmedState();
    // when
    var res = reservation.lock();
    // then
    Assertions.assertThat(res.isFailure()).isEqualTo(true);
  }

  @DisplayName("Should lock when number of locked reservations equals 2")
  @Test
  void shouldLockWhenNumberOfLockedReservationsEquals2() {
    // given
    var reservation = ReservationFixture.withTwoLocksWithDateMoreThan2Weeks();
    // when
    var result = reservation.lock();
    // then
    Assertions.assertThat(result.isSuccess()).isEqualTo(true);
  }

  @DisplayName("Should not lock when number of locked reservations equals 3")
  @Test
  void shouldNotLockWhenNumberOfLockedReservationsEquals3() {
    // given
    var reservation = ReservationFixture.withThreeLocks();
    // when
    var result = reservation.lock();
    // then
    Assertions.assertThat(result.isFailure()).isTrue();
  }

  @DisplayName("Should lock when departure date more than 2 weeks")
  @Test
  void shouldLockWhenDepartureDateMoreThan2Weeks() {
    // given
    var reservation = ReservationFixture.departureDateMoreThanTwoWeeks();
    //when
    var result = reservation.lock();
    // then
    Assertions.assertThat(result.isSuccess()).isTrue();
  }

  @DisplayName("Should not lock when departure date less than 2 weeks")
  @Test
  void shouldNotLockWhenDepartureDateLessThan2Weeks() {
    // given
    var reservation = ReservationFixture.departureDateLessThanTwoWeeks();
    //when
    var result = reservation.lock();
    // then
    Assertions.assertThat(result.isFailure()).isTrue();
  }

  @DisplayName("Should reschedule reservation when it is confirmed")
  @Test
  void shouldRescheduleReservationWhenItIsConfirmed() {
    // given
    var reservation = ReservationFixture.inConfirmedState();
    //when
    var result = reservation.reschedule();
    // then
    Assertions.assertThat(result.isSuccess()).isTrue();
  }

  @DisplayName("Should not reschedule reservation when it is locked")
  @Test
  void shouldNotRescheduleReservationWhenItIsLocked() {
    // given
    var reservation = ReservationFixture.inLockedState();
    //when
    var result = reservation.reschedule();
    // then
    Assertions.assertThat(result.isFailure()).isTrue();
  }

  @DisplayName("Should reschedule reservation when reschedule first time")
  @Test
  void shouldRescheduleReservationWhenRescheduleFirstTime() {
    // given
    var reservation = ReservationFixture.inConfirmedState();
    //when
    var result = reservation.reschedule();
    // then
    Assertions.assertThat(result.isSuccess()).isTrue();
  }

  @DisplayName("Should not reschedule when rescheduled three times")
  @Test
  void shouldNotRescheduleWhenRescheduledThreeTimes() {
    // given
    var reservation = ReservationFixture.inConfirmedStateRescheduledThreeTimes();
    //when
    var result = reservation.reschedule();
    // then
    Assertions.assertThat(result.isFailure()).isTrue();
  }

  @DisplayName("Should cancel when reservation is confirmed")
  @Test
  void shouldCancelWhenReservationIsConfirmed() {
    // given
    var reservation = ReservationFixture.inConfirmedState();
    //when
    var result = reservation.cancel();
    // then
    Assertions.assertThat(result.isSuccess()).isTrue();
  }

  @DisplayName("Should not cancel when reservation is locked")
  @Test
  void shouldNotCancelWhenReservationIsLocked() {
    // given
    var reservation = ReservationFixture.inLockedState();
    //when
    var result = reservation.cancel();
    // then
    Assertions.assertThat(result.isFailure()).isTrue();
  }

  @DisplayName("Should not cancel when reservation is activated")
  @Test
  void shouldNotCancelWhenReservationIsActivated() {
    // given
    var reservation = ReservationFixture.inActiveState();
    //when
    var result = reservation.cancel();
    // then
    Assertions.assertThat(result.isFailure()).isTrue();

  }
}
