package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.UUID;

class InitialReservation implements IdentifiedReservation {
  @Getter private final ReservationId id;
  private final ReservedThisMonth reservedThisMonth;
  private final CustomerId customerId;
  private final FligtId flightId;
  @Getter private final Status status;

  public InitialReservation(
      ReservedThisMonth reservedThisMonth, CustomerId customerId, FligtId flightId) {
    this.id = ReservationId.of(UUID.randomUUID());
    this.reservedThisMonth = reservedThisMonth;
    this.customerId = customerId;
    this.flightId = flightId;
    this.status = Status.NEW;
  }

  public static Result create(int reservedThisMonth, FligtId flightId, CustomerId customerId) {

    var reserved = ReservedThisMonth.of(reservedThisMonth);

    if (reserved.limitReached()) {
      return Result.failure();
    }

    return Result.successWithReturn(new InitialReservation(reserved, customerId, flightId));
  }

  // BLUE CARD
  public Result register(SeatNumber withSeat, Money withPrice, LocalDateTime withDepartureDate) {
    return RegisteredReservation.create(this, withSeat, withPrice, withDepartureDate);
  }

  @AllArgsConstructor
  public static class ReservedThisMonth {
    private static final int RESERVATIONS_PER_MONTH_LIMIT = 10;
    private int bookedThisMonth;

    public static ReservedThisMonth of(int reservedInMonth) {
      return new ReservedThisMonth(reservedInMonth);
    }

    boolean limitReached() {
      return bookedThisMonth >= RESERVATIONS_PER_MONTH_LIMIT;
    }

    void add() {
      bookedThisMonth++;
    }
  }
}
