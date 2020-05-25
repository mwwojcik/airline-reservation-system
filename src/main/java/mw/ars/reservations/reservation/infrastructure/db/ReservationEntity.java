package mw.ars.reservations.reservation.infrastructure.db;

import mw.ars.commons.model.FlightId;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.dto.StatusDTO;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.model.*;
import org.javamoney.moneta.Money;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class ReservationEntity {
  @MongoId private UUID reservationId;

  private UUID customerId;

  private UUID flightId;

  private String status;

  private LocalDateTime createdDate;

  private int seat;

  private BigDecimal price;

  private LocalDateTime departureDate;

  public ReservationEntity(UUID reservationId, LocalDateTime createdDate, UUID customerId, UUID flightId, String status) {
    this.reservationId = reservationId;
    this.createdDate = createdDate;
    this.customerId = customerId;
    this.flightId = flightId;
    this.status = status;
  }

  public static ReservationEntity from(InitialReservation reservation) {
    return new ReservationEntity(
        reservation.getId().getId(),
        reservation.getCreated(),
        reservation.getCustomerId().getId(),
        reservation.getFlightId().getId(),
        reservation.getStatus().name());

  }
  public void merge(RegisteredReservation reservation) {
    this.status=reservation.getStatus().name();
    this.departureDate=reservation.getDepartureDate();
    this.price=reservation.getPrice().getNumberStripped();
    this.seat=reservation.getSeat().getNumber();
  }

  public void merge(HoldedReservation reservation){
    this.status=reservation.getStatus().name();
  }

  public IdentifiedReservation toDomain() {
    var status = Status.valueOf(this.status);
    switch (status) {
      case NEW:
        return InitialReservation.of(
            ReservationId.of(reservationId), FlightId.of(flightId), CustomerId.of(customerId));
      case REGISTERED:
        return RegisteredReservation.of(
            ReservationId.of(reservationId),
            SeatNumber.of(seat),
            Money.of(price, "USD"),
            departureDate);
      case HOLDED:
        return null;
      case CANCELED:
        return null;
      case CONFIRMED:
        return null;
      case RESCHEDULED:
        return null;
    }
    return null;
  }

  public ReservationDTO toDTO() {
    return new ReservationDTO(
        ReservationId.of(reservationId),
        StatusDTO.valueOf(status),
        CustomerId.of(customerId),
        FlightId.of(flightId));
  }
}
