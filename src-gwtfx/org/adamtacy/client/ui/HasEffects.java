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
package org.adamtacy.client.ui;

import org.adamtacy.client.ui.effects.NEffect;
import org.adamtacy.client.ui.effects.exception.NEffectNotInterruptableException;
import org.adamtacy.client.ui.effects.transitionsphysics.TransitionPhysics;

import com.google.gwt.user.client.ui.Widget;

public interface HasEffects {

  /**
   * Adds an Effect to the panel.
   * 
   * @param theEffect The Effect.
   */
  public abstract void addEffect(NEffect theEffect);

  /*
   * 
   */
  public abstract void cancelEffects() throws NEffectNotInterruptableException;

  /**
   * Returns whatever widget might be held by this panel. This will change
   * depending upon whether this panel is floating or not.
   */
  public abstract Widget getPanelWidget();

  /**
   * Inverts all the effects attached to the panel.
   */
  public abstract void invertEffects();

  /**
   * Plays the effect from start to end.
   */
  public abstract void playEffects();

  /**
   * Plays the effect from a defined start point to a defined end point. Start
   * and End points are defined between 0.0 and 1.0 (if start is higher than
   * end, the effect plays in reverse).
   * 
   * @param start Start point of effect, value between 0.0 and 1.0.
   * @param end End point of effect, value between 0.0 and 1.0.
   */
  public abstract void playEffects(double start, double end);

  /**
   * Removes an Effect.
   * 
   * @parma theEffect The Effect.
   */
  public abstract void removeEffect(NEffect theEffect);

  /**
   * Starts playing an effect in a backwards direction from where it has currently
   * progressed to.
   */
  public abstract void resumeEffectsBackwards();

  /**
   * Starts playing an effect in a forwards direction from where it has
   * currently progressed to.
   */
  public abstract void resumeEffectsForwards();

  /**
   * Sets the effect length of all effects on the EffectPanel (all effects will
   * be set to have the same length).
   */
  public abstract void setEffectsLength(double duration);

  /**
   * Sets the effect transition for all effects added to the panel.
   * 
   * @param newTransition
   */
  public abstract void setEffectsTransitionType(TransitionPhysics newTransition);

}