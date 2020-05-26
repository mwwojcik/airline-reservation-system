package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.ReservationAppService;
import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.domain.*;
import mw.ars.reservations.reservation.model.InitialReservation;
import mw.ars.sales.flights.FlightsFacade;
import org.javamoney.moneta.Money;

import java.util.List;

public class DefaultReservationAppService implements ReservationAppService {

  private ReservationRepository repo;
  private FlightsFacade flightsFacade;

  public DefaultReservationAppService(ReservationRepository repo, FlightsFacade flightsFacade) {
    this.repo = repo;
    this.flightsFacade = flightsFacade;
  }

  @Override
  public List<ReservationDTO> findByFlightId(FindByFlightIdCommand command) {
    return repo.findByFlightId(command.getCustomerId(), command.getFlightId());
  }

  @Override
  public ReservationId create(CreateReservationCommand command) {
    int reservedThisMonth =
        repo.countReservationsAfterDate(
            InitialReservation.ReservedThisMonth.firstDateOfCurrentMonth());
    var created = CreateReservationDomainService.create(reservedThisMonth, command);
    repo.save(created);
    return created.getId();
  }

  @Override
  public void register(RegistrationCommand command) {
    var reservation = repo.findByReservationId(command.getReservationId()).orElseThrow();
    var flightid = ((InitialReservation) reservation).getFlightId();
    var flight =
        flightsFacade
            .findByFlightId(flightid)
            .orElseThrow(() -> new IllegalStateException("Flight not found!"));
    var registered = RegisterReservationDomainService.register(reservation, command, flight);
    repo.save(registered);
  }

  @Override
  public void holdOn(HoldOnReservationCommand command) {
    var reservation = repo.findByReservationId(command.getReservationId()).orElseThrow();
    int currentlyHolded = repo.countCurrentlyHolded();
    var holded = HoldOnReservationDomainService.hold(reservation, currentlyHolded);
    repo.save(holded);
  }

  @Override
  public void confirm(ConfirmationCommand command) {
    var reservation = repo.findByReservationId(command.getReservationId()).orElseThrow();
    var confirmed = ConfirmReservationDomainService.confirm(reservation);
    repo.save(confirmed);
  }

  @Override
  public ReservationId reschedule(RescheduleCommand command) {
    var original = repo.findByReservationId(command.getOriginal()).orElseThrow();

    int rescheduledSoFar = 0;

    var res =
        RescheduleReservationDomainService.reschedule(
            command.getCustomerId(),
            original,
            rescheduledSoFar,
            command.getNewFlightId(),
            command.getNewDepartureTime(),
            command.getNewSeatNumber(),
            Money.of(140, "USD"));

    repo.save(res.getConfirmed());
    repo.save(res.getRescheduled());

    return res.getConfirmed().getId();
  }

  @Override
  public void cancel(CancelByResrvationId command) {}
}
