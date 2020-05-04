package mw.ars.reservations.reservation.model;

import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.UUID;

class ReservationFixture {

  public static InitialReservation initial() {
    return (InitialReservation)
        InitialReservation.create(
                1, FligtId.of(UUID.randomUUID()), CustomerId.of(UUID.randomUUID()))
            .returned();
  }

  public static RegisteredReservation registeredWithDepartureDayFor3Weeks() {
    return registered(21);
  }

  public static RegisteredReservation registeredWithDepartureDayForOneWeek() {
    return registered(7);
  }

  private static RegisteredReservation registered(int numberOfDaysFromToday) {
    var anyInitial = ReservationFixture.initial();
    var anySeat = SeatNumber.of(10);
    var anyDepartureDate = LocalDateTime.now().plusDays(numberOfDaysFromToday);
    var anyPrice = Money.of(10, "USD");
    // when
    var res = anyInitial.register(anySeat, anyPrice, anyDepartureDate);
    return (RegisteredReservation) res.returned();
  }


  public static ConfirmedReservation confirmed() {
    var registeredReservation = registeredWithDepartureDayForOneWeek();
    return (ConfirmedReservation)registeredReservation.confirm().returned();
   }



  /*public static Reservation simple() {
    return reservation(simpleInitialReservation(), 0, 0, 0);
  }

  public static InitialReservation simpleInitialReservation() {
    return initial(0);
  }

  public static InitialReservation initialReservationWithMontlhyReservationLimit() {
    return initial(10);
  }

  private static InitialReservation initial(int reservedThisMonth) {
    return InitialReservation.of(
        InitialReservation.ReservedThisMonth.of(reservedThisMonth),
        FligtId.of(UUID.randomUUID()),
        CustomerId.of(UUID.randomUUID()));
  }

  private static Reservation reservation(
      InitialReservation initial, int lockedSoFar, int rescheduledSoFar) {
    return Reservation.from(
        initial,
        LocalDateTime.now(),
        Money.of(new BigDecimal(10), Monetary.getCurrency("USD")),
        SeatNumber.of(2),
        Reservation.CurrentlyLocked.of(lockedSoFar),
        Reservation.RescheduledSoFar.of(rescheduledSoFar));
  }

  private static Reservation reservation(
      InitialReservation initial,
      int lockedSoFar,
      int rescheduledSoFar,
      LocalDateTime departureDate) {
    return Reservation.from(
        initial,
        departureDate,
        Money.of(new BigDecimal(10), Monetary.getCurrency("USD")),
        SeatNumber.of(2),
        Reservation.CurrentlyLocked.of(lockedSoFar),
        Reservation.RescheduledSoFar.of(rescheduledSoFar));
  }

  private static Reservation reservation(LocalDateTime departureDate) {
    return Reservation.from(
        initial(0),
        departureDate,
        Money.of(new BigDecimal(10), Monetary.getCurrency("USD")),
        SeatNumber.of(2),
        Reservation.CurrentlyLocked.of(0),
        Reservation.RescheduledSoFar.of(0));
  }*/

  /*public static Reservation withMaxReservationPerMonth() {
    return reservation(ini,10, 0);
  }*/

  /*public static Reservation inActiveState() {
    var simple = simple();
    simple.activate();
    return simple;
  }*/

  /*public static Reservation inConfirmedState() {
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
    return reservation(2, 0, 0);
  }

  public static Reservation withThreeLocks() {
    return reservation(3, 0, 0);
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
  }*/
}
