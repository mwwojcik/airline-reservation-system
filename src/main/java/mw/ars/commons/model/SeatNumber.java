package mw.ars.commons.model;

import lombok.Value;

@Value
public class SeatNumber{
  private int number;

  private SeatNumber(int number){
    this.number=number;
  }

  public static SeatNumber of(int number){
    return new SeatNumber(number);
  }

}
