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
 * Interface controlling what each TransitionPhysics implementation needs to provide
 * 
 * Transition Physics can be chained together simply in order to allow the ordered 
 * application of Transition Physics.
 * 
 * All Transition Physics should implement this interface, however, in reality, all Transition
 * Physics implementations are expected to be a subclass of TransitionBase in order that chaining
 * of Transition Physics can be implemented.
 * 
 * @author Adam Tacy
 * @version 3.0
 *
 */
public interface TransitionPhysics {
  
  /**
   * The heart of the transition physics.
   * Takes a number between 0 and 1 (actual position) and 
   * returns a value representing the animation position.
   * 
   * Most transitions will provide a return value between 0 and 1.
   * @param input
   * @return
   */
  public double getAnimationPosition(double input);
  
  /**
   * Allows a Transition physics to be applied before this is applied.  You may wish to do this
   * to apply, say an Easing transition to an Elastic transition without having to create a completely
   * new class.
   * 
   * In effect if this transition is a, the parameter to this method is b, and the input 
   * value to the getAnimationPosition is v, then the Transition Physics formula applied is
   * 
   *     a(b(v))
   *     
   * Any number of transitions physics can be chained together and the resultant calculation
   * adheres to the order.
   * 
   * @param before
   */
  public void chainTransitionPhysicsBefore(TransitionPhysics before);
  
  /**
  * Allows a Transition physics to be applied after this is applied.    You may wish to do this
  * to apply, say an Easing transition to an Elastic transition without having to create a completely
  * new class.
  * 
  * In effect if this transition is a, the parameter to this method is b, and the input 
  * value to the getAnimationPosition is v, then the Transition Physics formula applied is
  * 
  *     b(a(v))
  *     
  * Any number of transitions physics can be chained together and the resultant calculation
  * adheres to the order.
  * 
  * **/
  public void chainTransitionPhysicsAfter(TransitionPhysics after);
}
