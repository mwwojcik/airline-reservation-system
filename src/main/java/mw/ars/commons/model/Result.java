package mw.ars.commons.model;

public class Result {
 private boolean res;

 public Result(boolean res) {
  this.res = res;
 }

 public static Result success() {
    return new Result(true);
  }

  public static Result failure() {
    return new Result(false);
  }
}
