package mw.ars.reservations.reservation.common.commands;

import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;

import java.time.LocalDateTime;

public class RegisterReservationCommnad{
    private ReservationId reservationId;
    private SeatNumber seat;
    private LocalDateTime departureDate;

    private RegisterReservationCommnad(ReservationId reservationId, SeatNumber seat, LocalDateTime departureDate){
        this.reservationId=reservationId;
        this.seat = seat;
        this.departureDate = departureDate;
    }

    public static RegisterReservationCommnad of(ReservationId reservationId,SeatNumber withSeat,LocalDateTime withDepartureDate){
        return new RegisterReservationCommnad(reservationId,withSeat,withDepartureDate);
    }
}