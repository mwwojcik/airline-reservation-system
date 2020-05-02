package mw.ars.reservations.reservation.infrastructure.db;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReservationRepositoryDB extends CrudRepository<ReservationEntity, UUID> {
}
