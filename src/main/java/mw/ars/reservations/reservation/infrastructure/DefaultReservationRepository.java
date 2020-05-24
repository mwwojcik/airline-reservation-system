package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.infrastructure.db.ReservationEntity;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.reservations.reservation.model.IdentifiedReservation;
import mw.ars.reservations.reservation.model.InitialReservation;
import mw.ars.reservations.reservation.model.RegisteredReservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultReservationRepository implements ReservationRepository {

  private ReservationRepositoryDB repositoryDB;

  public DefaultReservationRepository(ReservationRepositoryDB repositoryDB) {
    this.repositoryDB = repositoryDB;
  }

  @Override
  public ReservationId save(InitialReservation reservation) {
    repositoryDB.save(ReservationEntity.from((reservation)));
    return reservation.getId();
  }

  @Override
  public void save(RegisteredReservation reservation) {
    var entity =
        repositoryDB
            .findById(reservation.getId().getId())
            .orElseThrow(() -> new IllegalStateException("Entity not found!"));
    entity.merge(reservation);
    repositoryDB.save(entity);
  }

  @Override
  public List<ReservationDTO> findByFlightId(CustomerId customerId, FlightId flightId) {
    var res = repositoryDB.findByCustomerIdAndFlightId(customerId.getId(), flightId.getId());
    return res.stream().map(ReservationEntity::toDTO).collect(Collectors.toList());
  }

  @Override
  public Optional<IdentifiedReservation> findByReservationId(ReservationId reservationId) {
    var result = repositoryDB.findById(reservationId.getId());
    if (result.isEmpty()) {
      return Optional.empty();
    }
    return result.map(ReservationEntity::toDomain);
  }

  @Override
  public int countReservationsAfterDate(LocalDateTime firstDateOfCurrentMonth) {
    return repositoryDB.findByCreatedDateGreaterThan(firstDateOfCurrentMonth).size();
  }

  /* @Override
  public Optional<ReservationDTO> findByFlightId(FindByFlightIdCommand command) {
      return Optional.empty();
  }

  @Override
  public void holdOn(HoldOnReservationCommand command) {

  }

  @Override
  public Optional<ReservationDTO> findByReservationId(FindByReservationIdCommnad command) {
      return Optional.empty();
  }

  @Override
  public void confirm(ConfirmationCommand command) {

  }

  @Override
  public ReservationId reschedule(RescheduleCommand of) {
      return null;
  }

  @Override
  public void cancel(CancelByResrvationId command) {

  }

  @Override
  public void register(RegistrationCommand command) {

  }*/
}
