package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.ReservationService;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.domain.CreateReservationDomainService;
import mw.ars.reservations.reservation.model.InitialReservation;

import java.util.List;
import java.util.Optional;

public class DefaultReservationService implements ReservationService {

  private ReservationRepository repo;

  public DefaultReservationService(ReservationRepository repo) {
    this.repo = repo;
  }

  @Override
  public ReservationId create(CreateReservationCommand command) {
    // todo repo.countReservedThisMonth();
    int reservedThisMonth = 0;
    var result = CreateReservationDomainService.initialReservation(reservedThisMonth, command);
    var reservation = (InitialReservation) result.returned();
    repo.save(reservation);
    return reservation.getId();
  }

  @Override
  public List<ReservationDTO> findByFlightId(FindByFlightIdCommand command) {
    return repo.findByFlightId(command.getCustomerId(),command.getFligtId());
  }

  @Override
  public void register(RegistrationCommand command) {
    var reservation = repo.findByReservationId(command.getReservationId()).orElseThrow();
    System.out.println("");
  }

  @Override
  public void holdOn(HoldOnReservationCommand command) {}

  @Override
  public Optional<ReservationDTO> findByReservationId(FindByReservationIdCommnad command) {
    return Optional.empty();
  }

  @Override
  public void confirm(ConfirmationCommand command) {}

  @Override
  public ReservationId reschedule(RescheduleCommand of) {
    return null;
  }

  @Override
  public void cancel(CancelByResrvationId command) {}
}
