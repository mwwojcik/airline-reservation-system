package mw.ars.reservations.reservation.infrastructure.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepositoryDB  extends MongoRepository<ReservationEntity,String> {
}
