package mw.ars.reservations.reservation.infrastructure.db;

import mw.ars.reservations.reservation.common.model.CustomerId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepositoryDB  extends MongoRepository<ReservationEntity,UUID> {
     List<ReservationEntity> findByCustomerIdAndFlightId(UUID CustomerId, UUID flightId);
}
