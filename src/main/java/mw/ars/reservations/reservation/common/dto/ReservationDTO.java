package mw.ars.reservations.reservation.common.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import mw.ars.commons.model.CustomerId;
import mw.ars.commons.model.FlightId;
import mw.ars.commons.model.ReservationId;
import org.springframework.web.bind.annotation.GetMapping;


@Getter
public class ReservationDTO {
    private ReservationId reservationId;
    private StatusDTO status;
    private CustomerId customerId;
    private FlightId flightId;

    private ReservationDTO(ReservationId reservationId, StatusDTO status, CustomerId customerId, FlightId flightId) {
        this.reservationId = reservationId;
        this.status = status;
        this.customerId = customerId;
        this.flightId = flightId;
    }

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

    public static ReservationDTO create(ReservationId reservationId,StatusDTO status,CustomerId customerId,FlightId flightId) {
     return new ReservationDTO(reservationId,status,customerId,flightId) ;
     }

}
