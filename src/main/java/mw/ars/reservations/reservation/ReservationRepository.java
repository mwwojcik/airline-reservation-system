package mw.ars.reservations.reservation;

import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.model.IdentifiedReservation;

import java.util.List;

public interface ReservationRepository {

    ReservationId save(IdentifiedReservation save);

    List<ReservationDTO> findByFlightId(CustomerId customerId, FligtId fligtId);

    /*Optional<ReservationDTO> findByFlightId(FindByFlightIdCommand command);

    void holdOn(HoldOnReservationCommand command);

    Optional<ReservationDTO> findByReservationId(FindByReservationIdCommnad command);

    void confirm(ConfirmationCommand command);

    ReservationId reschedule(RescheduleCommand of);

    void cancel(CancelByResrvationId command);

    void register(RegistrationCommand command);*/
}
