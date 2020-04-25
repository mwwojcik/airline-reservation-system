package mw.ars.reservations.reservation.model;

import mw.ars.commons.model.Result;

public class Reservation {
  //BLUE CARD
  public Result create() {
    if(limitReservationPerMonthReached()){
      return Result.failure();
    }
    return Result.success();
  }

  //BLUE CARD
  public Result confirm() {
    if(!(isCreated()||isLocked())){
      return Result.failure();
    }
    return Result.success();
  }
  //BLUE CARD
  public Result lock() {
    if(!isCreated()||departureDateLessThan()||limitLockedReservationReached()){
        return Result.failure();
    }
    return Result.success();
  }

  //BLUE CARD
  public Result reschedule() {
    if(!isConfirmed()||limitReschedulingReached()){
      return Result.failure();
    }
    return Result.success();
  }

  //BLUE CARD
  public Result cancel() {
    if(!(isLocked()||isConfirmed())){
      return Result.failure();
    }
    return Result.success();
  }




  private boolean isConfirmed() {
    return false;
  }

  private boolean limitReschedulingReached() {
    return false;
  }

  private boolean isCreated() {
    return false;
  }

  private boolean isLocked() {
    return false;
  }

  private boolean limitReservationPerMonthReached() {
    return false;
  }

  private boolean departureDateLessThan() {
    return false;
  }

  private boolean limitLockedReservationReached() {
    return false;
  }
}
