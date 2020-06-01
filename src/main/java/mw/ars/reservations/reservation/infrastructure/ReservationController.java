package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
      @PathVariable("id") String reservationId) {
    return fasade
        .findDetailsByReservationId(ReservationId.of(UUID.fromString(reservationId)))
        .map(it -> ResponseEntity.status(HttpStatus.OK).body(it))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<ReservationDTO>> findByFlightId(
      @RequestParam("customerId") String customerId, @RequestParam("flightId") String flightId) {
    // FindByFlightIdCommand command;
    var command = FindByFlightIdCommand.of(CustomerId.of(UUID.fromString(customerId)), FlightId.of(UUID.fromString(flightId)));
    var result = fasade.findByFlightId(command);
    // status OK with result
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/create")
  public ResponseEntity<?> create(
      @RequestParam("customerId") String customerId, @RequestParam("flightId") String flightId) {
    var command = CreateReservationCommand.of(CustomerId.of(UUID.fromString(customerId)), FlightId.of(UUID.fromString(flightId)));
    var reservationId = fasade.create(command);
    var uri = "/api/reservations/%s".format(reservationId.getId().toString());
    return ResponseEntity.created(URI.create(uri)).build();
  }

  @PutMapping("/{id}/hold")
  public ResponseEntity holdOn(@PathVariable("id") String reservationId) {
    var command = HoldOnReservationCommand.of(ReservationId.of(UUID.fromString(reservationId)));
    fasade.holdOn(command);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/confirm")
  public ResponseEntity confirm(@PathVariable("id") String reservationId) {
    var command = ConfirmationCommand.of(ReservationId.of(UUID.fromString(reservationId)));
    fasade.confirm(command);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/register")
  public ResponseEntity register(
      @PathVariable("id") String reservationId,
      @RequestParam("withSeat") int withSeat,
      @RequestParam("flightId") String flightId,
      @RequestParam("departureTime") LocalDateTime departureTime) {
    var command =
        RegistrationCommand.of(
            ReservationId.of(UUID.fromString(reservationId)),
            FlightId.of(UUID.fromString(flightId)),
            departureTime,
            SeatNumber.of(withSeat));
    fasade.register(command);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/reschedule")
  public ResponseEntity<?> reschedule(
      @PathVariable("id") String originalReservationId,
      @RequestParam("customerId") String customerId,
      @RequestParam("newFlightId") String newFlightId,
      @RequestParam("newSeatNumber") int newSeatNumber,
      @RequestParam("newDepartureTime") LocalDateTime newDepartureTime) {
    var command =
        RescheduleCommand.of(
            ReservationId.of(UUID.fromString(originalReservationId)),
            CustomerId.of(UUID.fromString(customerId)),
            FlightId.of(UUID.fromString(newFlightId)),
            SeatNumber.of(newSeatNumber),
            newDepartureTime);

    var result = fasade.reschedule(command);
    var uri = "/api/reservations/%s".format(result.getId().toString());
    return ResponseEntity.created(URI.create(uri)).build();
  }

  @DeleteMapping("/{id}/cancel")
  public ResponseEntity cancel(@PathVariable("id") String reservationId) {
    var command = CancelByResrvationId.of(ReservationId.of(UUID.fromString(reservationId)));
    fasade.cancel(command);
    return ResponseEntity.ok().build();
  }
}
