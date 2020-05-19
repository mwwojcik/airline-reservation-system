package mw.ars.reservations.reservation.infrastructure.db;

import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;



@Value
@Document
public
class ReservationEntity {
    private String id;

}
