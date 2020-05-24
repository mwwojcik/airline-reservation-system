package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.*;
import mw.ars.commons.model.FlightId;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

public class InitialReservation implements IdentifiedReservation {
  @Getter private final ReservationId id;
  @Getter private final CustomerId customerId;
  @Getter private final FlightId flightId;
  @Getter private final Status status;
  @Getter private LocalDateTime created;

  private InitialReservation(CustomerId customerId, FlightId flightId) {
    this.id = ReservationId.of(UUID.randomUUID());
    this.customerId = customerId;
    this.flightId = flightId;
    this.status = Status.NEW;
    created = LocalDateTime.now();
  }

  private InitialReservation(ReservationId reservationId, CustomerId customerId, FlightId flightId) {
    this.id = reservationId;
    this.customerId = customerId;
    this.flightId = flightId;
    this.status = Status.NEW;
  }

  public static InitialReservation of(
          ReservationId reservationId, FlightId flightId, CustomerId customerId) {
    return new InitialReservation(reservationId, customerId, flightId);
  }

  public static Result create(int reservedThisMonth, FlightId flightId, CustomerId customerId) {

    var reserved = ReservedThisMonth.of(reservedThisMonth);

    if (reserved.limitReached()) {
      return Result.failure();
    }

    return Result.successWithReturn(new InitialReservation(customerId, flightId));
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

    public static LocalDateTime firstDateOfCurrentMonth() {
      LocalDateTime firstDayOfCurrentMonth =
          LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
      return firstDayOfCurrentMonth;
    }

    boolean limitReached() {
      return bookedThisMonth >= RESERVATIONS_PER_MONTH_LIMIT;
    }

    void add() {
      bookedThisMonth++;
    }
  }
}
