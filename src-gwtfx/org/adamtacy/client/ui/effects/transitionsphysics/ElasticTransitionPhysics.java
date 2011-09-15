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
 * Transition physics that emulates a spring animation. Effect will overshoot,
 * then oscillate until settling down.
 * 
 * The number of bounces and dampening factor can be configured.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class ElasticTransitionPhysics extends TransitionBase {

  private double bounces;

  private double dampnerFactor;

  /**
   * Default constructor will reques for 6.5 bounces with a dampening factor of
   * 3
   */
  public ElasticTransitionPhysics() {
    this(6.5, 3);
  }

  /**
   * Constructor allowing configuration of parameters
   * 
   * @param bounces Number of oscillations - should be a half number (e.g 6.5)
   *          so that effect finishes at position 1.0
   * @param dampnerFactor How quickly dampening happens - higher the number, the
   *          quicker
   */
  public ElasticTransitionPhysics(double bounces, double dampnerFactor) {
    this.dampnerFactor = dampnerFactor;
    this.bounces = bounces;
  }
  /**
   * Determine animation position.
   */
  public double applyTransitionPhysics(double input) {
    return 1 - (Math.cos(input * bounces * Math.PI) * Math.exp(-input
        * dampnerFactor));
  }

  public double getBounces() {
    return bounces;
  }

  public double getDampnerFactor() {
    return dampnerFactor;
  }

  /**
   * There are no guards for the result of the Elastic transition, we just
   * return the result that has been calculated.
   */
  @Override
  protected double guardResult(double input) {
    return input;
  }

  public void setBounces(double newBounce) {
    bounces = newBounce;
  }

  public void setDampnerFactor(double dampnerFactor) {
    this.dampnerFactor = dampnerFactor;
  }
}
