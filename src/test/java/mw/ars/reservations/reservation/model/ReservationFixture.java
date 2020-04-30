package mw.ars.reservations.reservation.model;

import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class ReservationFixture {

  public static Reservation simple() { return reservation(0,0,0); }



  private static Reservation reservation(int lockedSoFar, int reservedThisMonth, int rescheduledSoFar) {
    return Reservation.of(
            CustomerId.of(UUID.randomUUID()),
            FligtId.of(UUID.randomUUID()),
            LocalDateTime.now(),
            Money.of(new BigDecimal(10), Monetary.getCurrency("USD")),
            SeatNumber.of(2),
            Reservation.CurrentlyLocked.of(lockedSoFar),
            Reservation.ReservedThisMonth.of(reservedThisMonth),
            Reservation.RescheduledSoFar.of(rescheduledSoFar));

  }

  private static Reservation reservation(int lockedSoFar, int reservedThisMonth, int rescheduledSoFar,LocalDateTime departureDate) {
    return Reservation.of(
            CustomerId.of(UUID.randomUUID()),
            FligtId.of(UUID.randomUUID()),
           departureDate,
            Money.of(new BigDecimal(10), Monetary.getCurrency("USD")),
            SeatNumber.of(2),
            Reservation.CurrentlyLocked.of(lockedSoFar),
            Reservation.ReservedThisMonth.of(reservedThisMonth),
            Reservation.RescheduledSoFar.of(rescheduledSoFar));

  }

  private static Reservation reservation(LocalDateTime departureDate) {
    return Reservation.of(
            CustomerId.of(UUID.randomUUID()),
            FligtId.of(UUID.randomUUID()),
            departureDate,
            Money.of(new BigDecimal(10), Monetary.getCurrency("USD")),
            SeatNumber.of(2),
            Reservation.CurrentlyLocked.of(0),
            Reservation.ReservedThisMonth.of(0),
            Reservation.RescheduledSoFar.of(0));

  }

  public static Reservation withMaxReservationPerMonth() {
    return reservation(0,10,0);
  }

  public static Reservation inActiveState() {
    var simple = simple();
    simple.activate();
    return simple;
  }

  public static Reservation inConfirmedState() {
    var confirmed = simple();
    confirmed.activate();
    confirmed.confirm();
    return confirmed;
  }

  public static Reservation inLockedState() {
    var confirmed = simple();
    confirmed.activate();
    confirmed.lock();
    return confirmed;
  }

  public static Reservation withTwoLocks() {
    return reservation(2,0,0);
  }

  public static Reservation withThreeLocks() {
    return reservation(3,0,0);
  }

  public static Reservation departureDateMoreThanTwoWeeks() {
    var reservation = reservation(LocalDateTime.now().plusDays(20));
    reservation.activate();
    return reservation;
  }

  public static Reservation departureDateLessThanTwoWeeks() {
    var reservation = reservation(LocalDateTime.now().plusDays(10));
    reservation.activate();
    return reservation;

  }

  public static Reservation inConfirmedStateRescheduledThreeTimes() {
    var reservation = reservation(0, 0, 3);
    reservation.activate();
    reservation.confirm();
    return reservation;
  }

  public static Reservation withTwoLocksWithDateMoreThan2Weeks() {
    var reservation = reservation(2, 0, 2, LocalDateTime.now().plusDays(20));
    reservation.activate();
    return reservation;
  }
}
