package mw.ars.commons.model;

public class Result<T> {
  private boolean res;
  private String message;
  private T returnObject;

  public Result(boolean res, String message) {
    this.res = res;
    this.message = message;
  }

  public Result(boolean res, String message,T returnObject) {
    this.res = res;
    this.message = message;
    this.returnObject=returnObject;
  }

  public static Result success() {
    return new Result(true, "ok");
  }

  public static <R> Result successWithReturn(R returned){
    return new Result<R>(true,"OK",returned);
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

  public T returned(){return returnObject;}
}
