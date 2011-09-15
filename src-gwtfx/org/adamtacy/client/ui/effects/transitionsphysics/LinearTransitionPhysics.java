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
 * Simple TransitionPhysics that returns the value input as the output.
 * 
 * @author Adam Tacy
 * @version 3.0
 *
 */
public class LinearTransitionPhysics extends TransitionBase {

  /**
   * Linear transition physics just returns the value provided to it, i.e.
   * amimation position = actual position
   */
  public double applyTransitionPhysics(double input) {
    return input;
  }

  /**
   * We could be lazy here and ignore guards since informally this will return whatever is passed in.
   * However, as this transition could be chained together with a transition whose result is greater than
   * 1,0 or less than 0,0 , then we should apply the formal guards.
   */
  @Override
  protected double guardResult(double input) {
    if (input>1.0) return 1.0;
    else if (input < 0.0) return 0.0;
    else return input;
  }
}
