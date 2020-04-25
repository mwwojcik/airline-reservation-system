package mw.ars.reservations.reservation.model;

import mw.ars.commons.model.Result;

public class Reservation {
  public Result create() {
    return Result.success();
  }
  public Result confirm() {
    return Result.success();
  }
  public Result lock() {
    return Result.success();
  }

  public Result reschedule() {
    return Result.success();
  }

  public Result cancel() {
    return Result.success();
  }
}
