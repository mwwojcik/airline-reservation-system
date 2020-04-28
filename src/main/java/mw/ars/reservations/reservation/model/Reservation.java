package mw.ars.reservations.reservation.model;

import mw.ars.commons.model.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Reservation {

  private LockedReservation lockedReservation;
  private MonthlyReservation monthlyReservation;
  private RescheduledReservation rescheduledReservation;

  private Status currentStatus;
  private LocalDateTime departureDate;

  // BLUE CARD

  public Result create() {
    if (monthlyReservation.limitReached()) {
      return Result.failure();
    }
    currentStatus = Status.NEW;
    monthlyReservation.create();
    return Result.success();
  }

  // BLUE CARD
  public Result confirm() {
    if (!(isCreated() || isLocked())) {
      return Result.failure();
    }
    currentStatus = Status.CONFIRMED;
    return Result.success();
  }
  // BLUE CARD
  public Result lock() {
    if (!isCreated() || departureDateLessThan() || lockedReservation.limitReached()) {
      return Result.failure();
    }
    currentStatus = Status.LOCKED;
    lockedReservation.lock();
    return Result.success();
  }
  // BLUE CARD

  public Result reschedule() {
    if (!isConfirmed() || rescheduledReservation.limitReached()) {
      return Result.failure();
    }
    rescheduledReservation.reschedule();
    currentStatus = Status.RESCHEDULED;
    return Result.success();
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

  private boolean isCreated() {
    return currentStatus == Status.NEW;
  }

  private boolean isLocked() {
    return currentStatus == Status.LOCKED;
  }

  private boolean departureDateLessThan() {
    return Period.between(LocalDate.now(), departureDate.toLocalDate()).getDays()
        < LockedReservation.TWO_WEEKS_DAYS;
  }

  enum Status {
    NEW,
    LOCKED,
    CONFIRMED,
    CANCELED,
    RESCHEDULED;
  }

  public static class MonthlyReservation {

    private static int RESERVATIONS_PER_MONTH_LIMIT = 10;
    private int reservedInThisMonth;

    boolean limitReached() {
      return reservedInThisMonth == RESERVATIONS_PER_MONTH_LIMIT;
    }

    void create() {
      reservedInThisMonth++;
    }
  }

  public static class RescheduledReservation {

    static int RESCHEDULING_LIMIT = 3;
    int rescheduledSoFar;

    boolean limitReached() {
      return rescheduledSoFar == RESCHEDULING_LIMIT;
    }

    void reschedule() {
      rescheduledSoFar++;
    }
  }

  public static class LockedReservation {

    private static int LOCKED_RESERVATION_LIMIT = 3;

    private static int TWO_WEEKS_DAYS = 14;
    private int lockedSoFar;

    boolean limitReached() {
      return lockedSoFar == LOCKED_RESERVATION_LIMIT;
    }

    void lock() {
      lockedSoFar++;
    }
  }
}
