package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationFacade;
import mw.ars.reservations.reservation.ReservationService;
import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.commons.model.ReservationId;
import mw.ars.sales.flights.FlightsFacade;

import java.util.List;

public class DefaultReservationFacade implements ReservationFacade {

    private ReservationService service;

    public DefaultReservationFacade(ReservationService service) {
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
