package mw.ars.reservations.reservation.infrastructure.db;

import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.dto.StatusDTO;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.model.InitialReservation;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document
public class ReservationEntity {
  @MongoId private UUID reservationId;

  private UUID customerId;

  private UUID flightId;

  public ReservationEntity(UUID reservationId, UUID customerId, UUID flightId) {
    this.reservationId = reservationId;
    this.customerId = customerId;
    this.flightId = flightId;
  }

  public static ReservationEntity from(InitialReservation reservation) {
    return new ReservationEntity(
        reservation.getId().getId(),
        reservation.getCustomerId().getId(),
        reservation.getFlightId().getId());
  }

  public ReservationDTO toDTO() {
    return new ReservationDTO(
        ReservationId.of(reservationId),
        StatusDTO.NEW,
        CustomerId.of(customerId),
        FligtId.of(flightId));
  }
}
