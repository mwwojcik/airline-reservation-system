package mw.ars.reservations.reservation.infrastructure.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepositoryDB  extends MongoRepository<ReservationEntity,UUID> {
     List<ReservationEntity> findByCustomerIdAndFlightId(UUID CustomerId, UUID flightId);
     List<ReservationEntity> findByCreatedDateGreaterThan(LocalDateTime startDate);
}
