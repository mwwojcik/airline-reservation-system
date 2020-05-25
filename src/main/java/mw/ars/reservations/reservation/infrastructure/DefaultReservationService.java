package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.ReservationService;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.domain.CreateReservationDomainService;
import mw.ars.reservations.reservation.domain.HoldOnReservationDomainService;
import mw.ars.reservations.reservation.domain.RegisterReservationDomainService;
import mw.ars.reservations.reservation.model.InitialReservation;
import mw.ars.reservations.reservation.model.RegisteredReservation;
import mw.ars.sales.flights.FlightsFacade;
import mw.ars.sales.flights.common.dto.FlightDTO;

import java.util.List;

public class DefaultReservationService implements ReservationService {

  private ReservationRepository repo;
  private FlightsFacade flightsFacade;

  public DefaultReservationService(ReservationRepository repo, FlightsFacade flightsFacade) {
    this.repo = repo;
    this.flightsFacade = flightsFacade;
  }

  @Override
  public List<ReservationDTO> findByFlightId(FindByFlightIdCommand command) {
    return repo.findByFlightId(command.getCustomerId(),command.getFlightId());
  }

  @Override
  public ReservationId create(CreateReservationCommand command) {
    int reservedThisMonth = repo.countReservationsAfterDate(InitialReservation.ReservedThisMonth.firstDateOfCurrentMonth());
    var created = CreateReservationDomainService.create(reservedThisMonth, command);
    repo.save(created);
    return created.getId();
  }

  @Override
  public void register(RegistrationCommand command) {
    var reservation = repo.findByReservationId(command.getReservationId()).orElseThrow();
    var flight = flightsFacade.findByFlightId(reservation.getFlightId()).orElseThrow(()->new IllegalStateException("Flight not found!"));
    var registered=RegisterReservationDomainService.register(reservation,command,flight);
    repo.save(registered);
  }

  @Override
  public void holdOn(HoldOnReservationCommand command) {
    var reservation = repo.findByReservationId(command.getReservationId()).orElseThrow();
    int currentlyHolded=repo.countCurrentlyHolded();
    var holded=HoldOnReservationDomainService.hold(reservation,currentlyHolded);
    repo.save(holded);
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
