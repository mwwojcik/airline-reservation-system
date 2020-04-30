package mw.ars.reservations.reservation.common.model;

public class SeatNumber{
  private int number;

  private SeatNumber(int number){
    this.number=number;
  }

  public static SeatNumber of(int number){
    return new SeatNumber(number);
  }

}
