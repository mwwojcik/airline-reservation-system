package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Reservation {
  private static int TWO_WEEKS_DAYS = 14;
  private ReservationId id;
  private CustomerId customerId;
  private FligtId flightId;
  private LocalDateTime departureDate;
  private Money price;
  private SeatNumber seat;
  private ReservationId parentResId;

  private CurrentlyLocked currentlyLocked;
  private ReservedThisMonth reservedThisMonth;
  private RescheduledSoFar rescheduledSoFar;
  private Status currentStatus;

  private Reservation(
      CustomerId customerId,
      FligtId flightId,
      LocalDateTime departureDate,
      Money price,
      SeatNumber seat,
      CurrentlyLocked currentlyLocked,
      ReservedThisMonth reservedThisMonth,
      RescheduledSoFar rescheduledSoFar) {

    this.id = ReservationId.of(UUID.randomUUID());
    this.currentStatus = Status.NEW;

    this.seat = seat;
    this.customerId = customerId;
    this.flightId = flightId;
    this.departureDate = departureDate;
    this.price = price;
    this.currentlyLocked = currentlyLocked;
    this.reservedThisMonth = reservedThisMonth;
    this.rescheduledSoFar = rescheduledSoFar;
    parentResId = null;
  }

  private Reservation(ReservationBuilder reservationBuilder) {
    this.seat = reservationBuilder.seat;
    this.id = ReservationId.of(UUID.randomUUID());
    this.customerId = reservationBuilder.customerId;
    this.flightId = reservationBuilder.flightId;
    this.departureDate = reservationBuilder.departureDate;
    this.price = reservationBuilder.price;
    this.currentlyLocked = reservationBuilder.currentlyLocked;
    this.reservedThisMonth = reservationBuilder.reservedThisMonth;
    this.rescheduledSoFar = reservationBuilder.rescheduledSoFar;
    this.currentStatus = reservationBuilder.currentStatus;
    this.parentResId = reservationBuilder.parentResId;
  }

  public static Reservation of(
      CustomerId customerId,
      FligtId flightId,
      LocalDateTime departureDate,
      Money price,
      SeatNumber seat,
      CurrentlyLocked currentlyLocked,
      ReservedThisMonth reservedThisMonth,
      RescheduledSoFar rescheduledSoFar) {

    return new Reservation(
        customerId,
        flightId,
        departureDate,
        price,
        seat,
        currentlyLocked,
        reservedThisMonth,
        rescheduledSoFar);
  }

  // BLUE CARD
  public Result activate() {
    if (reservedThisMonth.limitReached()) {
      return Result.failure();
    }
    currentStatus = Status.ACTIVE;
    reservedThisMonth.create();
    return Result.success();
  }

  // BLUE CARD
  public Result confirm() {
    if (!(isActive() || isLocked())) {
      return Result.failure();
    }
    currentStatus = Status.CONFIRMED;
    return Result.success();
  }
  // BLUE CARD
  public Result lock() {
    if (!isActive() || departureDateLessThan() || currentlyLocked.limitReached()) {
      return Result.failure();
    }
    currentStatus = Status.LOCKED;
    currentlyLocked.add();
    return Result.success();
  }
  // BLUE CARD

  public Result reschedule() {
    if (!isConfirmed() || rescheduledSoFar.limitReached()) {
      return Result.failure();
    }
    rescheduledSoFar.add();
    currentStatus = Status.RESCHEDULED;

    return Result.successWithReturn(
        new ReservationBuilder()
            .customerId(customerId)
            .rescheduledSoFar(rescheduledSoFar)
            .currentlyLocked(currentlyLocked)
            .reservedThisMonth(reservedThisMonth)
            .currentStatus(Status.CONFIRMED)
            .parentResId(ReservationId.of(id.getId())));
  }
  // BLUE CARD

  public Result cancel() {
    if (!(isLocked() || isConfirmed())) {
      return Result.failure();
    }
    currentStatus = Status.CANCELED;
    return Result.success();
  }

  private boolean isConfirmed() {
    return currentStatus == Status.CONFIRMED;
  }

  private boolean isActive() {
    return currentStatus == Status.ACTIVE;
  }

  private boolean isLocked() {
    return currentStatus == Status.LOCKED;
  }

  private boolean departureDateLessThan() {
    return Period.between(LocalDate.now(), departureDate.toLocalDate()).getDays() < TWO_WEEKS_DAYS;
  }

  enum Status {
    NEW,
    LOCKED,
    CONFIRMED,
    CANCELED,
    RESCHEDULED,
    ACTIVE
  }

  @AllArgsConstructor
  public static class ReservedThisMonth {
    private static int RESERVATIONS_PER_MONTH_LIMIT = 10;
    private int bookedThisMonth;

    public static ReservedThisMonth of(int reservedInMonth) {
      return new ReservedThisMonth(reservedInMonth);
    }

    boolean limitReached() {
      return bookedThisMonth == RESERVATIONS_PER_MONTH_LIMIT;
    }

    void create() {
      bookedThisMonth++;
    }
  }

  @AllArgsConstructor
  public static class RescheduledSoFar {

    static int RESCHEDULING_LIMIT = 3;
    int rescheduledSoFar;

    public static RescheduledSoFar of(int rescheduledSoFar) {
      return new RescheduledSoFar(rescheduledSoFar);
    }

    boolean limitReached() {
      return rescheduledSoFar == RESCHEDULING_LIMIT;
    }

    void add() {
      rescheduledSoFar++;
    }
  }

  @AllArgsConstructor
  public static class CurrentlyLocked {
    private static int LOCKED_RESERVATION_LIMIT = 3;
    private int lockedSoFar;

    public static CurrentlyLocked of(int lockedSoFar) {
      return new CurrentlyLocked(lockedSoFar);
    }

    boolean limitReached() {
      return lockedSoFar == LOCKED_RESERVATION_LIMIT;
    }

    void add() {
      lockedSoFar++;
    }
  }

  static class ReservationBuilder {
    private SeatNumber seat;
    private CustomerId customerId;
    private FligtId flightId;
    private LocalDateTime departureDate;
    private Money price;
    private CurrentlyLocked currentlyLocked;
    private ReservedThisMonth reservedThisMonth;
    private RescheduledSoFar rescheduledSoFar;
    private Status currentStatus;
    private ReservationId parentResId;

    ReservationBuilder seat(final SeatNumber seat) {
      this.seat = seat;
      return this;
    }

    ReservationBuilder flightId(final FligtId flightId) {
      this.flightId = flightId;
      return this;
    }

    ReservationBuilder parentResId(final ReservationId parentResId) {
      this.parentResId = parentResId;
      return this;
    }

    ReservationBuilder price(final Money price) {
      this.price = price;
      return this;
    }

    ReservationBuilder departureDate(final LocalDateTime departureDate) {
      this.departureDate = departureDate;
      return this;
    }

    Reservation build() {
      return new Reservation(this);
    }

    private ReservationBuilder customerId(final CustomerId customerId) {
      this.customerId = customerId;
      return this;
    }

    private ReservationBuilder currentlyLocked(final CurrentlyLocked currentlyLocked) {
      this.currentlyLocked = currentlyLocked;
      return this;
    }

    private ReservationBuilder reservedThisMonth(final ReservedThisMonth reservedThisMonth) {
      this.reservedThisMonth = reservedThisMonth;
      return this;
    }

    private ReservationBuilder rescheduledSoFar(final RescheduledSoFar rescheduledSoFar) {
      this.rescheduledSoFar = rescheduledSoFar;
      return this;
    }

    private ReservationBuilder currentStatus(final Status currentStatus) {
      this.currentStatus = currentStatus;
      return this;
    }
  }
}
