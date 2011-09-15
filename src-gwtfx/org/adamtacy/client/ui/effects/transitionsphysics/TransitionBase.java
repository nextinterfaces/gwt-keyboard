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
 * Base class implementing the TransitionPhysics interface and providing the
 * ability to chain Transition Physics together.
 * 
 * It is expected that all TransitionPhysics implementations are a subclass of
 * this, and that all of their getAnimationPosition() methods call
 * applyBeforeTransition() first, then calculate the position themselves, and
 * then call applyAfterTranslation().
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public abstract class TransitionBase implements TransitionPhysics {

  /**
   * List of transactionPhysics to apply prior to the actual transition (initially none, i.e. null)
   */
  protected TransitionCollection orderedTransitionPhysicsAfter;
  
  /**
   * List of transactionPhysics to apply after the actual transition (initially none, i.e. null)
   */
  protected TransitionCollection orderedTransitionPhysicsBefore;

  /**
   * Method to apply the list of Transition Physics that should be applied after the actual 
   * transition physics defined.
   * 
   * Should be the called last in any implementation of the getAnimationPosition() method.
   * 
   * @param input The input to the calling TransitionPhysics..
   * @return The newly calculated value of animation position after applying all listed post TransitionPhysics to the input.
   */
  protected double applyAfterTransitions(double input) {
    if (orderedTransitionPhysicsAfter != null)
      return orderedTransitionPhysicsAfter.applyAnimationPositionCalculations(input);
    else
      return input;
  }

  /**
   * Method to apply the list of Transition Physics that should be applied prior to the actual 
   * transition physics defined.
   * 
   * Should be the called first in any implementation of the getAnimationPosition() method.
   * 
   * @param input The input to the calling TransitionPhysics..
   * @return The newly calculated value of animation position after applying all listed pre TransitionPhysics to the input.
   */
  protected double applyBeforeTransitions(double input) {
    if (orderedTransitionPhysicsBefore != null)
      return orderedTransitionPhysicsBefore.applyAnimationPositionCalculations(input);
    else
      return input;
  }

  /**
   * Add a new TransitionPhysics to the list to be applied after the actual Transaction 
   * Physics has been implemented.
   */
  public void chainTransitionPhysicsAfter(TransitionPhysics after) {
    if (orderedTransitionPhysicsAfter == null)
      orderedTransitionPhysicsAfter = new TransitionCollection();
    orderedTransitionPhysicsAfter.add(after);
  }

  /**
   * Add a new TransitionPhysics to the list to be applied before the actual Transaction
   * Physics has been implemented.
   */
  public void chainTransitionPhysicsBefore(TransitionPhysics before) {
    if (orderedTransitionPhysicsBefore == null)
      orderedTransitionPhysicsBefore = new TransitionCollection();
    orderedTransitionPhysicsBefore.insertElementAt(before, 0);
  }

  /**
   * The getAnimationProgress() method returns the progress of animation according to 
   * the definition of this TransitionPhysics - note it is still abstract in this class.
   */
  public double getAnimationPosition(double input){
    // Apply the pre TransitionPhysics (if there are any);
    input = this.applyBeforeTransitions(input);
    
    // Apply the easing for the subclass of this.
    input = applyTransitionPhysics(input);
    
    // Apply the post TransitionPhysics (if there are any).
    input = this.applyAfterTransitions(input);
    
    // Return the guarded value.
    return guardResult(input);
  }
  
  /**
   * Abstract method to allow the implementation of the maths formula 
   * @param input
   * @return
   */
  protected abstract double applyTransitionPhysics(double input);
  
  /**
   * Abstract method to allow the guarding of a result before it is returned, i.e.
   * this is the last formula applied before a result is returned to the effect.  Often
   * it is expected that this method will ensure the returned value is between 0,0 and 1,0.
   * 
   * @param input The input value
   * @return The result of applying a set of guards to the input.
   */
  protected abstract double guardResult(double input);
}
