package mw.ars.reservations.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mw.ars.commons.model.Result;
import mw.ars.reservations.reservation.common.model.CustomerId;
import mw.ars.reservations.reservation.common.model.FligtId;
import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CancelledReservation {
    @Getter private ReservationId id;
    @Getter private Status status;
    private LocalDateTime cancellationDate ;

    private CancelledReservation(ReservationId id) {
        this.id=id;
        status=Status.CANCELED;
        this.cancellationDate=LocalDateTime.now();
    }


    public static Result create(ConfirmedReservation confirmed) {
        return Result.successWithReturn(new CancelledReservation(confirmed.getId()));
    }

    public static Result create(HoldedReservation holded) {
        return Result.successWithReturn(new CancelledReservation(holded.getId()));
    }

}
