package mw.ars.reservations.reservation;

import mw.ars.reservations.reservation.common.commands.*;
import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;

import java.util.List;

public interface ReservationFacade {

    ReservationId create(CreateReservationCommand command);

    List<ReservationDTO> findByFlightId(FindByFlightIdCommand command);

    void holdOn(HoldOnReservationCommand command);

    void confirm(ConfirmationCommand command);

    ReservationId reschedule(RescheduleCommand of);

    void cancel(CancelByResrvationId command);

    void register(RegistrationCommand command);
}
