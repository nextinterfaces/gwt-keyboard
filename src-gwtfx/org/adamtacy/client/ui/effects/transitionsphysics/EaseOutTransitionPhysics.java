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

public class EaseOutTransitionPhysics extends EaseBase{

  /**
   * For Ease Out Transition we use the formula below for easing.
   * It allows for an easing factor to be applies - the greater 
   * the factor, the quicker the progress approaches the end point.
   * 
   * The value for the factor (including the default case) is defined in the EaseBase class;
   * and this method is called by the getAnimationPosition() method defined in EaseBase.
   * 
   * @author Adam Tacy
   * @version 3.0
   * 
   */
  protected double applyTransitionPhysics(double input){
    return 1 - Math.pow(1 - input, factor * 2);
  }
 
  /**
   * Simple constructor.
   */
  public EaseOutTransitionPhysics(){
  }
  
  /**
   * Constructor that allows the setting of the factor initialy.
   * @param factor
   */
  public EaseOutTransitionPhysics(double factor){
    this.factor = factor;
  }
}
