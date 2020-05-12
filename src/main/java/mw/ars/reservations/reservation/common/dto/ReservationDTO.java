package mw.ars.reservations.reservation.common.dto;

import lombok.Value;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;


@Value
public class ReservationDTO {
    private ReservationId reservationId;
    private StatusDTO status;
    private CustomerId customerId;
    private FligtId fligtId;

    public boolean isNew() {
     return status==StatusDTO.NEW ;
     }

    public boolean isHolded() {
        return status==StatusDTO.HOLDED;
    }

    public boolean isConfirmed() {
        return status==StatusDTO.CONFIRMED;

    }

    public boolean isRescheduled() {
        return status==StatusDTO.RESCHEDULED;

    }

    public boolean isCancelled() {
        return status==StatusDTO.CANCELED;

    }

    public boolean isRegistered() {
        return status==StatusDTO.REGISTERED;

    }
}
