package mw.ars.reservations.reservation.infrastructure.db;

import lombok.Value;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Value
class ReservationEntity {
    @Id
    private UUID id;

}
