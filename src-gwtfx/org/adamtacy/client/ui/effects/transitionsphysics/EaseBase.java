/*
 * Copyright 2008-2009 Adam Tacy <adam.tacy AT gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.adamtacy.client.ui.effects.transitionsphysics;

/**
 * 
 * Base class that contains a number of common factors to easing transitions.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public abstract class EaseBase extends TransitionBase{

  /**
   * The factor can be used to control the amount of easing applied.
   */
  protected double factor = 1.0;

  /**
   * Simple setter for the factor value
   * @param newFactor The new factor to be applied to the easing function.
   */
  public void setEaseFactor(double newFactor) {
    this.factor = newFactor;
  }
  
  /**
   * Simple getter for the factor value applied to the easing function.
   * @return
   */
  public double getEaseFactor() {
    return factor;
  }
  
  /**
   * Function to guard the returned value - for an easing the value should never be greater than 1,0 or lower than 0,0
   * (this constrasts, for example, with an elastic transition where progress could be greater than 1,0 at times.
   * 
   * @param input
   * @return
   */
  protected double guardResult(double input){
    if (input > 1) input = 1.0;
    if (input < 0) input = 0.0;
    return input;
  }

  /**
   * This function remains abstract since it is expected to be implemented by individual transition physics.
   */  
  protected abstract double applyTransitionPhysics(double input);
  
}
