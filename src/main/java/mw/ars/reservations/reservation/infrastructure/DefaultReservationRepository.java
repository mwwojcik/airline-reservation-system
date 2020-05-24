package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.common.commands.FindByFlightIdCommand;
import mw.ars.reservations.reservation.common.commands.RegistrationCommand;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.infrastructure.db.ReservationEntity;
import mw.ars.reservations.reservation.infrastructure.db.ReservationRepositoryDB;
import mw.ars.reservations.reservation.model.IdentifiedReservation;
import mw.ars.reservations.reservation.model.InitialReservation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultReservationRepository implements ReservationRepository {

  private ReservationRepositoryDB repositoryDB;

  public DefaultReservationRepository(ReservationRepositoryDB repositoryDB) {
    this.repositoryDB = repositoryDB;
  }

  @Override
  public ReservationId save(IdentifiedReservation res) {
    repositoryDB.save(ReservationEntity.from(((InitialReservation) res)));
    return res.getId();
  }

  @Override
  public List<ReservationDTO> findByFlightId(CustomerId customerId, FligtId fligtId) {
    var res = repositoryDB.findByCustomerIdAndFlightId(customerId.getId(),fligtId.getId());
    return res.stream().map(ReservationEntity::toDTO).collect(Collectors.toList());
  }

  @Override
  public Optional<IdentifiedReservation> findByReservationId(ReservationId reservationId) {
    var result = repositoryDB.findById(reservationId.getId());
    if(result.isEmpty()){
      return Optional.empty();
    }
    return result.map(ReservationEntity::toDomain);
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
