package mw.ars.reservations.reservation.infrastructure.db;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.commons.model.SeatNumber;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.dto.StatusDTO;
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

  private UUID originalReservationId;

  public ReservationEntity() {
  }

  public ReservationEntity(
      UUID reservationId,
      LocalDateTime createdDate,
      UUID customerId,
      UUID flightId,
      String status) {
    this.reservationId = reservationId;
    this.createdDate = createdDate;
    this.customerId = customerId;
    this.flightId = flightId;
    this.status = status;
  }

  public ReservationEntity(
      UUID reservationId,
      LocalDateTime createdDate,
      UUID customerId,
      UUID flightId,
      String status,
      int seat,
      BigDecimal price,
      LocalDateTime departureDate,
      UUID originalId) {

    this.reservationId = reservationId;
    this.createdDate = createdDate;
    this.customerId = customerId;
    this.flightId = flightId;
    this.status = status;
    this.seat=seat;
    this.price=price;
    this.departureDate=departureDate;
    this.originalReservationId=originalId;

  }

  public static ReservationEntity from(InitialReservation reservation) {
    return new ReservationEntity(
        reservation.getId().getId(),
        reservation.getCreated(),
        reservation.getCustomerId().getId(),
        reservation.getFlightId().getId(),
        reservation.getStatus().name());
  }

  public static ReservationEntity from(ConfirmedAfterRescheduling reservation) {
    return new ReservationEntity(
        reservation.getId().getId(),
        reservation.getCreated(),
        reservation.getCustomerId().getId(),
        reservation.getFlightId().getId(),
        reservation.getStatus().name(),
        reservation.getSeat().getNumber(),
        reservation.getPrice().getNumberStripped(),
        reservation.getDepartureDate(),
        reservation.getOriginalReservationId().getId());
  }

  public void merge(RegisteredReservation reservation) {
    this.status = reservation.getStatus().name();
    this.departureDate = reservation.getDepartureDate();
    this.price = reservation.getPrice().getNumberStripped();
    this.seat = reservation.getSeat().getNumber();
  }

  public void merge(HoldedReservation reservation) {
    this.status = reservation.getStatus().name();
  }

  public void merge(ConfirmedReservation reservation) {
    this.status = reservation.getStatus().name();
  }



  public void merge(RescheduledReservation reservation) {
    this.status = reservation.getStatus().name();
  }

  public void merge(CancelledReservation reservation) {
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
        return HoldedReservation.of(ReservationId.of(reservationId));
      case CANCELED:
        return CancelledReservation.of(ReservationId.of(reservationId));
      case CONFIRMED:
        return ConfirmedReservation.of(ReservationId.of(reservationId));
      case RESCHEDULED:
        return RescheduledReservation.of(ReservationId.of(reservationId));
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
