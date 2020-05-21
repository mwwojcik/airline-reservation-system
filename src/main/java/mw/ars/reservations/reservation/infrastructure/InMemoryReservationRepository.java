package mw.ars.reservations.reservation.infrastructure;

import mw.ars.reservations.reservation.ReservationRepository;
import mw.ars.reservations.reservation.common.dto.ReservationDTO;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.model.IdentifiedReservation;

import java.util.List;
import java.util.Optional;

public class InMemoryReservationRepository implements ReservationRepository {
    public void clearAll() {
     }

    @Override
    public ReservationId save(IdentifiedReservation save) {
        return null;
    }

    @Override
    public List<ReservationDTO> findByFlightId(CustomerId customerId, FligtId fligtId) {
        return List.of();
    }

    /*@Override
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
