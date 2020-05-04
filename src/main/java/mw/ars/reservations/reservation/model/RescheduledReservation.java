package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class RescheduledReservation {
  private static final int TWO_WEEKS_DAYS = 14;
  @Getter private final ReservationId id;
  @Getter private final Status status;
  private final ReservationId originalReservationId;
  private final SeatNumber seat;
  private final Money price;
  private final LocalDateTime departureDate;
  private final RescheduledSoFar rescheduledSoFar;
  private final FligtId flightId;

  private RescheduledReservation(Builder builder) {
    this.id = ReservationId.of(UUID.randomUUID());
    this.status = Status.CONFIRMED;
    this.originalReservationId = builder.originalReservationId;
    this.seat = builder.seat;
    this.price = builder.price;
    this.departureDate = builder.departureDate;
    this.rescheduledSoFar = builder.rescheduledSoFar;
    this.flightId = builder.flightId;
  }

  public static Result create(
      ConfirmedReservation confirmed,
      int rescheduled,
      FligtId newFlightId,
      SeatNumber newSeatNumber,
      LocalDateTime newDepartureDate,
      Money newPrice) {

    var rescheduledSoFar = RescheduledSoFar.of(rescheduled);

    if (rescheduledSoFar.limitReached()) {
      return Result.failure();
    }

    rescheduledSoFar.add();

    return Result.successWithReturn(
        new Builder()
            .departureDate(newDepartureDate)
            .originalReservationId(confirmed.getId())
            .seat(newSeatNumber)
            .flightId(newFlightId)
            .price(newPrice)
            .build());
  }
  // BLUE CARD

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

  /** Builder for instances of type {@link RescheduledReservation} */
  public static final class Builder {
    private FligtId flightId;
    private ReservationId originalReservationId;
    private SeatNumber seat;
    private Money price;
    private LocalDateTime departureDate;
    private RescheduledSoFar rescheduledSoFar;

    /**
     * Set the value of the field status of the target instance of type {@link
     * RescheduledReservation}
     */
    public Builder flightId(final FligtId flighId) {
      this.flightId = flighId;
      return this;
    }

    /**
     * Set the value of the field originalReservationId of the target instance of type {@link
     * RescheduledReservation}
     */
    public Builder originalReservationId(final ReservationId originalReservationId) {
      this.originalReservationId = originalReservationId;
      return this;
    }

    /**
     * Set the value of the field seat of the target instance of type {@link RescheduledReservation}
     */
    public Builder seat(final SeatNumber seat) {
      this.seat = seat;
      return this;
    }

    /**
     * Set the value of the field price of the target instance of type {@link
     * RescheduledReservation}
     */
    public Builder price(final Money price) {
      this.price = price;
      return this;
    }

    /**
     * Set the value of the field departureDate of the target instance of type {@link
     * RescheduledReservation}
     */
    public Builder departureDate(final LocalDateTime departureDate) {
      this.departureDate = departureDate;
      return this;
    }

    /**
     * Set the value of the field rescheduledSoFar of the target instance of type {@link
     * RescheduledReservation}
     */
    public Builder rescheduledSoFar(final RescheduledSoFar rescheduledSoFar) {
      this.rescheduledSoFar = rescheduledSoFar;
      return this;
    }

    /** Create a new instance of type {@link RescheduledReservation} */
    public RescheduledReservation build() {
      return new RescheduledReservation(this);
    }
  }
}
