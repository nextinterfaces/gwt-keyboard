package com.ywebb.tablet.shared;

import java.io.Serializable;

public class Question implements Serializable {

  private static final long serialVersionUID = -1516119323775635375L;

  // Who will get first penalty?
  // Will Vancouver score during this penalty?
  // Who will get the next penalty?
  // Will there be another goal before end of period 1?
  // Who will score the next goal?

  public enum Result {
    YES, NO
  }

  private Integer   id;
  private String    text;
  private Result    result;
  private PropState state;

  public Question(Integer id, String text) {
    this.text = text;
  }

  public Integer getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public PropState getState() {
    return state;
  }

  public void setState(PropState state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Question{" + id + ", " + state + ", " + result + "}";
  }

}
