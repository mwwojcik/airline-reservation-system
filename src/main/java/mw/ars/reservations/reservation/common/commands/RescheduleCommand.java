package mw.ars.reservations.reservation.common.commands;

import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;

import java.time.LocalDateTime;

public class RescheduleCommand {
  private ReservationId original;
  private FligtId newFlightId;
  private SeatNumber newSeatNumber;
  private LocalDateTime newDepartureTime;

  private RescheduleCommand(ReservationId original, FligtId newFlightId, SeatNumber newSeatNumber, LocalDateTime newDepartureTime) {
    this.original = original;
      this.newFlightId = newFlightId;
      this.newSeatNumber = newSeatNumber;
      this.newDepartureTime = newDepartureTime;
  }

  public static RescheduleCommand of(ReservationId original, FligtId newFlightId, SeatNumber newSeatNumber, LocalDateTime newDepartureTime) {
    return new RescheduleCommand(original, newFlightId, newSeatNumber, newDepartureTime);
  }
}
