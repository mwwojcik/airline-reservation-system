package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController("/api/reservations")
class ReservationController {
  private ReservationFacade fasade;

  ReservationController(ReservationFacade fasade) {
    this.fasade = fasade;
  }

  @GetMapping
  public List<ReservationDTO> findByFlightId() {
    // FindByFlightIdCommand command;
    return null;
  }

  @PostMapping("/{id}/create")
  public ReservationId create(UUID customerId, UUID flightId) {
    var command = CreateReservationCommand.of(CustomerId.of(customerId), FlightId.of(flightId));
    return null;
  }

  @PutMapping("/{id}/hold")
  public void holdOn(UUID reservationId) {
    var command = HoldOnReservationCommand.of(ReservationId.of(reservationId));
  }

  @PutMapping("/{id}/confirm")
  public void confirm(UUID reservationId) {
    var command = ConfirmationCommand.of(ReservationId.of(reservationId));
  }

  @PutMapping("/{id}/register")
  public void register(
      UUID reservationId, int withSeat, UUID flightId, LocalDateTime departureTime) {
    var command =
        RegistrationCommand.of(
            ReservationId.of(reservationId),
            FlightId.of(flightId),
            departureTime,
            SeatNumber.of(withSeat));
  }

  @PostMapping("/{id}/reschedule")
  public ReservationId reschedule(
      UUID originalReservationId,
      UUID customerId,
      UUID newFlightId,
      int newSeatNumber,
      LocalDateTime newDepartureTime) {
    var command =
        RescheduleCommand.of(
            ReservationId.of(originalReservationId),
            CustomerId.of(customerId),
            FlightId.of(newFlightId),
            SeatNumber.of(newSeatNumber),
            newDepartureTime);
    return null;
  }

  @DeleteMapping("/{id}/cancel")
  public void cancel(UUID reservationId) {
    var command = CancelByResrvationId.of(ReservationId.of(reservationId));
  }
}
