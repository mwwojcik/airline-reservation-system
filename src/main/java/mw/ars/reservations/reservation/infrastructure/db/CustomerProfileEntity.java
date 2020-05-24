package mw.ars.reservations.reservation.infrastructure.db;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Document
class CustomerProfileEntity {
    @MongoId
    private UUID reservationId;

}
