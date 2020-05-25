package mw.ars.reservations.reservation;

import mw.ars.commons.model.FlightId;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    ReservationId save(InitialReservation reservation);
    void  save(RegisteredReservation reservation);
    void  save(HoldedReservation reservation);
    void save(ConfirmedReservation confirmed);
    List<ReservationDTO> findByFlightId(CustomerId customerId, FlightId flightId);
    Optional<IdentifiedReservation> findByReservationId(ReservationId reservationId);
    int countReservationsAfterDate(LocalDateTime firstDateOfCurrentMonth);

    int countCurrentlyHolded();

    /*void holdOn(HoldOnReservationCommand command);


    void confirm(ConfirmationCommand command);

    ReservationId reschedule(RescheduleCommand of);

    void cancel(CancelByResrvationId command);

    */
}
