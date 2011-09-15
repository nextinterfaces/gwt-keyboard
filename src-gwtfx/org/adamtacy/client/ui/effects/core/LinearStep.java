package org.adamtacy.client.ui.effects.core;

public class LinearStep implements StepFunctionInterface {
  public double getPosition(double start, double end, double currentProgress){
    return start + (currentProgress * (end - start));
  }
}
