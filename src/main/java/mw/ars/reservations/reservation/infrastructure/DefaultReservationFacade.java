package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.ReservationAppService;
import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public class DefaultReservationFacade implements ReservationFacade {

  private ReservationAppService service;

  public DefaultReservationFacade(ReservationAppService service) {
    this.service = service;
  }

  @Override
  public ReservationId create(CreateReservationCommand command) {
    return service.create(command);
  }

  @Override
  public List<ReservationDTO> findByFlightId(FindByFlightIdCommand command) {
    return service.findByFlightId(command);
  }

  @Override
  public Optional<ReservationDTO> findDetailsByReservationId(ReservationId reservationId) {
    return service.findDetailsByReservationId(reservationId);
  }

  @Override
  public void holdOn(HoldOnReservationCommand command) {
    service.holdOn(command);
  }

  @Override
  public void confirm(ConfirmationCommand command) {
    service.confirm(command);
  }

  @Override
  public ReservationId reschedule(RescheduleCommand command) {
    return service.reschedule(command);
  }

  @Override
  public void cancel(CancelByResrvationId command) {
    service.cancel(command);
  }

  @Override
  public void register(RegistrationCommand command) {
    service.register(command);
  }
}
