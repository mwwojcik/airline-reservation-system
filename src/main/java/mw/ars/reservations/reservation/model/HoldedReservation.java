package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.ReservationId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@AllArgsConstructor
@Getter
public class HoldedReservation {
  private static int TWO_WEEKS_DAYS = 14;
  @Getter private ReservationId id;
  @Getter private Status status;
  private CurrentlyHolded currentlyHolded;

  private HoldedReservation(ReservationId id, CurrentlyHolded currentlyHolded) {
    this.id = id;
    this.currentlyHolded = currentlyHolded;
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
    return Result.successWithReturn(new HoldedReservation(registeredReservation.getId(), holded));
  }

  public Result cancel() {
    return CancelledReservation.create(this) ;
  }


  private static boolean lessThanTwoWeeks(LocalDateTime departureDate) {
    return Period.between(LocalDate.now(), departureDate.toLocalDate()).getDays() < TWO_WEEKS_DAYS;
  }

  @AllArgsConstructor
  public static class CurrentlyHolded {
    private static int HOLDED_RESERVATION_LIMIT = 3;
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
