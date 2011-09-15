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

import java.util.Iterator;
import java.util.Vector;

/**
 * Simple Collection class for holding a list of TransitionPhysics and applying them
 * in order to an input value.
 * 
 * @author Adam Tacy
 * @version 3.0
 */
@SuppressWarnings("serial")
public class TransitionCollection extends Vector<TransitionPhysics> {

  /**
   * Method to apply all getAnimationPosition() methods in the list of TransitionPhysiscs
   * held by an object of this class.
   * 
   * @param value Initial input value.
   * @return The result of applying the getAnimationPosition() method from all TransitionPhysics
   * held by this object; in the order that they were added to the collection.
   */
  public double applyAnimationPositionCalculations(double value){
    for (Iterator<TransitionPhysics> it = iterator(); it.hasNext();) {
      value = it.next().getAnimationPosition(value);
    }
    return value;
  }
  
}
