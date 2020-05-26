package mw.ars.reservations.reservation.infrastructure;

import mw.ars.commons.model.FlightId;
import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.ReservationId;
import mw.ars.reservations.reservation.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class InMemoryReservationRepository implements ReservationRepository {
    public void clearAll() {
     }


    @Override
    public ReservationId save(InitialReservation reservation) {
        return null;
    }

    @Override
    public void save(RegisteredReservation reservation) {

    }

    @Override
    public void save(HoldedReservation reservation) {

    }

    @Override
    public void save(ConfirmedReservation confirmed) {

    }

    @Override
    public void save(ConfirmedAfterRescheduling confirmed) {

    }

    @Override
    public List<ReservationDTO> findByFlightId(CustomerId customerId, FlightId flightId) {
        return List.of();
    }

    @Override
    public Optional<IdentifiedReservation> findByReservationId(ReservationId reservationId) {
        return Optional.empty();
    }

    @Override
    public int countReservationsAfterDate(LocalDateTime firstDateOfCurrentMonth) {
        return 0;
    }

    @Override
    public int countCurrentlyHolded() {
        return 0;
    }

    @Override
    public void save(RescheduledReservation rescheduled) {

    }




    /*@Override

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
