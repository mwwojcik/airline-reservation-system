package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private ReservationFacade fasade;

  ReservationController(ReservationFacade fasade) {
    this.fasade = fasade;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDTO> findDetailsByReservationId(
      @PathVariable("id") UUID reservationId) {
    return fasade
        .findDetailsByReservationId(ReservationId.of(reservationId))
        .map(
            it ->
                ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(it))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<ReservationDTO>> findByFlightId(
      @RequestParam("customerId") UUID customerId, @RequestParam("flightId") UUID flightId) {
    // FindByFlightIdCommand command;
    var command = FindByFlightIdCommand.of(CustomerId.of(customerId), FlightId.of(flightId));
    var result = fasade.findByFlightId(command);
    // status OK with result
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result);
  }

  @PostMapping("/create")
  public ResponseEntity<?> create(
      @RequestParam("customerId") UUID customerId, @RequestParam("flightId") UUID flightId) {
    var command = CreateReservationCommand.of(CustomerId.of(customerId), FlightId.of(flightId));
    var reservationId = fasade.create(command);
    var uri = "/api/reservations/%s".format(reservationId.getId().toString());
    return ResponseEntity.created(URI.create(uri)).build();
  }

  @PutMapping("/{id}/hold")
  public ResponseEntity holdOn(@PathVariable("id") UUID reservationId) {
    var command = HoldOnReservationCommand.of(ReservationId.of(reservationId));
    fasade.holdOn(command);
    return ResponseEntity.accepted().build();
  }

  @PutMapping("/{id}/confirm")
  public ResponseEntity confirm(@PathVariable("id") UUID reservationId) {
    var command = ConfirmationCommand.of(ReservationId.of(reservationId));
    fasade.confirm(command);
    return ResponseEntity.accepted().build();
  }

  @PutMapping("/{id}/register")
  public ResponseEntity register(
      @PathVariable("id") UUID reservationId,
      @RequestParam("withSeat") int withSeat,
      @RequestParam("flightId") UUID flightId,
      @RequestParam("departureTime") LocalDateTime departureTime) {
    var command =
        RegistrationCommand.of(
            ReservationId.of(reservationId),
            FlightId.of(flightId),
            departureTime,
            SeatNumber.of(withSeat));
    fasade.register(command);
    return ResponseEntity.accepted().build();
  }

  @PostMapping("/{id}/reschedule")
  public ResponseEntity<?> reschedule(
      @PathVariable("id") UUID originalReservationId,
      @RequestParam("customerId") UUID customerId,
      @RequestParam("newFlightId") UUID newFlightId,
      @RequestParam("newSeatNumber") int newSeatNumber,
      @RequestParam("newDepartureTime") LocalDateTime newDepartureTime) {
    var command =
        RescheduleCommand.of(
            ReservationId.of(originalReservationId),
            CustomerId.of(customerId),
            FlightId.of(newFlightId),
            SeatNumber.of(newSeatNumber),
            newDepartureTime);

    var result = fasade.reschedule(command);
    var uri = "/api/reservations/%s".format(result.getId().toString());
    return ResponseEntity.created(URI.create(uri)).build();
  }

  @DeleteMapping("/api/reservations/{id}/cancel")
  public ResponseEntity cancel(@PathVariable("id") UUID reservationId) {
    var command = CancelByResrvationId.of(ReservationId.of(reservationId));
    fasade.cancel(command);
    return ResponseEntity.noContent().build();
  }
}
