package mw.ars.commons.model;

public class Result{
  private boolean res;
  private String message;
  private Object returnObject;

  public Result(boolean res, String message) {
    this.res = res;
    this.message = message;
  }

  public Result(boolean res, String message,Object returnObject) {
    this.res = res;
    this.message = message;
    this.returnObject=returnObject;
  }

  public static Result success() {
    return new Result(true, "ok");
  }

  public static Result successWithReturn(Object returned){
    return new Result(true,"OK",returned);
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

  public Object returned(){return returnObject;}
}
