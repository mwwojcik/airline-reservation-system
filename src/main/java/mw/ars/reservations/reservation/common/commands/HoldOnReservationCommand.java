package mw.ars.reservations.reservation.common.commands;

import mw.ars.reservations.reservation.common.model.ReservationId;
import mw.ars.reservations.reservation.common.model.SeatNumber;

import java.time.LocalDateTime;

public class HoldOnReservationCommand {
    private ReservationId reservationId;
    private SeatNumber seat;
    private LocalDateTime departureDate;

    private HoldOnReservationCommand(ReservationId reservationId, SeatNumber seat, LocalDateTime departureDate){
        this.reservationId=reservationId;
        this.seat = seat;
        this.departureDate = departureDate;
    }

    public static HoldOnReservationCommand of(ReservationId reservationId, SeatNumber withSeat, LocalDateTime withDepartureDate){
        return new HoldOnReservationCommand(reservationId,withSeat,withDepartureDate);
    }
}