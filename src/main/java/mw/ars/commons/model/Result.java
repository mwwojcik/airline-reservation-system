package mw.ars.commons.model;

public class Result {
  private boolean res;
  private String cause;

  public Result(boolean res, String cause) {
    this.res = res;
    this.cause = cause;
  }

  public static Result success() {
    return new Result(true, "ok");
  }

  public static Result failure(String cause) {
    return new Result(false, cause);

  }
  public static Result failure() {
    return failure("failure");
  }
  public boolean isSuccess() {
    return res == true;
  }

  public boolean isFailure() {
    return res == false;
  }
}
