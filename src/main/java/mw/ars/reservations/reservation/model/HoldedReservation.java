package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@AllArgsConstructor
@Getter
public class HoldedReservation implements IdentifiedReservation {
  private static final int TWO_WEEKS_DAYS = 14;
  @Getter private final ReservationId id;
  @Getter private final Status status;

  private HoldedReservation(ReservationId id) {
    this.id = id;
    this.status = Status.HOLDED;
  }

  public static Result create(
      RegisteredReservation registeredReservation,
      LocalDateTime departureDate,
      int currentlyHolded) {
    var holded = CurrentlyHolded.of(currentlyHolded);
    if (lessThanTwoWeeks(departureDate) || holded.limitReached()) {
      return Result.failure();
    }
    holded.add();
    return Result.successWithReturn(new HoldedReservation(registeredReservation.getId()));
  }

  private static boolean lessThanTwoWeeks(LocalDateTime departureDate) {
    return Period.between(LocalDate.now(), departureDate.toLocalDate()).getDays() < TWO_WEEKS_DAYS;
  }

  public static HoldedReservation of(ReservationId reservationId) {
    return new HoldedReservation(reservationId,Status.HOLDED);
  }

  public Result cancel() {
    return CancelledReservation.create(this);
  }


  public Result confirm() {
    return ConfirmedReservation.create(this);
  }



  @AllArgsConstructor
  public static class CurrentlyHolded {
    private static final int HOLDED_RESERVATION_LIMIT = 3;
    private int holdedSoFar;

    public static CurrentlyHolded of(int holdedSoFar) {
      return new CurrentlyHolded(holdedSoFar);
    }

    boolean limitReached() {
      return holdedSoFar == HOLDED_RESERVATION_LIMIT;
    }

    void add() {
      holdedSoFar++;
    }
  }
}
