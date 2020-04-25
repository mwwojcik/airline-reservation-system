package mw.ars.reservations.reservation.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

  @DisplayName("Should create new reservation")
  @Test
  void shouldCreateNewReservation() {
    // given
    // when
    // then
  }

  @DisplayName("Should not create when reservations limit exceeded")
  @Test
  void shouldNotCreateReservationLimitExceeded() {
    // given
    // when
    // then
    // Fail.fail("Write your test");
  }

  @DisplayName("Should lock reservation when it is new")
  @Test
  void shouldLockCreatedReservation() {
    // given
    // when
    // then
    //    Fail.fail("Write your test");
  }

  @DisplayName("Should not lock when reservation is confirmed")
  @Test
  void shouldNotLockConfirmedReservation() {
    // given
    // when
    // then
    //     Fail.fail("Write your test");
  }

  @DisplayName("Should lock when number of locked reservations equals 2")
  @Test
  void shouldLockWhenNumberOfLockedReservationsEquals2() {
    // given
    // when
    // then
    //   Fail.fail("Write your test");
  }

  @DisplayName("Should not lock when number of locked reservations eqals 3")
  @Test
  void shouldNotLockWhenNumberOfLockedReservationsEqals3() {
    // given
    // when
    // then
    //    Fail.fail("Write your test");
  }

  @DisplayName("Should lock when departure date more than 2 weeks")
  @Test
  void shouldLockWhenDepartureDateMoreThan2Weeks() {
    // given
    // when
    // then
    //     Fail.fail("Write your test");
  }

  @DisplayName("Should not lock when departure date less than 2 weeks")
  @Test
  void shouldNotLockWhenDepartureDateLessThan2Weeks() {
    // given
    // when
    // then
    //      Fail.fail("Write your test");
  }

  @DisplayName("Should reschedule reservation when it is confirmed")
  @Test
  void shouldRescheduleReservationWhenItIsConfirmed() {
    // given
    // when
    // then
    //       Fail.fail("Write your test");
  }

  @DisplayName("Should not reschedule reservation when it is locked")
  @Test
  void shouldNotRescheduleReservationWhenItIsLocked() {
    // given
    // when
    // then
    //        Fail.fail("Write your test");
  }

  @DisplayName("Should reschedule reservation when reschedule first time")
  @Test
  void shouldRescheduleReservationWhenRescheduleFirstTime() {
    // given
    // when
    // then
    //         Fail.fail("Write your test");
  }

  @DisplayName("Should not reschedule when rescheduled two times")
  @Test
  void shouldNotRescheduleWhenRescheduledTwoTimes() {
    // given
    // when
    // then
    //          Fail.fail("Write your test");
  }

  @DisplayName("Should cancel when reservation is confirmed")
  @Test
  void shouldCancelWhenReservationIsConfirmed() {
    // given
    // when
    // then
    //           Fail.fail("Write your test");
  }

  @DisplayName("Should cancel when reservation is locked")
  @Test
  void shouldCancelWhenReservationIsLocked() {
    // given
    // when
    // then
    //            Fail.fail("Write your test");
  }

  @DisplayName("Shoould not cancel when reservation is new")
  @Test
  void shoouldNotCancelWhenReservationIsNew() {
    // given
    // when
    // then
    //             Fail.fail("Write your test");

  }
}
