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
package org.adamtacy.client.ui.effects;

import java.util.Iterator;

import org.adamtacy.client.ui.NEffectPanel;
import org.adamtacy.client.ui.effects.transitionsphysics.TransitionPhysics;

/**
 * An effect composed of two or more internal NEffects that play in parallel.
 */
public abstract class ParallellCompositeEffect extends NEffect {

  protected NEffectCollection internalEffects = new NEffectCollection();
  private double duration = 10;
  private int numberEffectsRegistered = 0;
  private boolean initialized = false;

  /**
   * Sets the duration (in seconds) of all internal effects.
   */
  public void setDuration(double new_EffectLengthSeconds) {
    setParallelEffectLength(new_EffectLengthSeconds);
  }

  /**
   * Sets the TransitionPhysics of all internal effects.
   */
  public void setTransitionType(TransitionPhysics newTP){
    internalEffects.setTransitionTypes(newTP);
  }

  @Override
  public void setUpEffect() {
    for (Iterator<NEffect> it = internalEffects.iterator(); it.hasNext();)
      ((NEffect) it).setUpEffect();
  }

  @Override
  public void tearDownEffect() {
    internalEffects.tearDownEffects();
  }

  protected void resetCompositeEffect() {
  }

  public void reset() {
    for (Iterator<NEffect> it = internalEffects.iterator(); it.hasNext();)
      ((ParallellCompositeEffect) it).reset();
    resetCompositeEffect();
  }

  public void beginCompositeEffect() {
  }

  public void play(double start, double end) {
    if (numberEffectsRegistered < 2)
      throw new RuntimeException(
          "A composite effect must have more than one effect registered");
    if (!initialized)
      throw new RuntimeException(
          "A composite effect must be initialized before attempting to use");
    beginCompositeEffect();

    for (Iterator<NEffect> it = internalEffects.iterator(); it.hasNext();) {
      NEffect theEffect = (NEffect) it.next();
      theEffect.play(start, end);
    }
  }
  
  public void play(){
    play(0.0, 1.0);
  }

  public void registerEffect(NEffect e) {
    internalEffects.add(e);
    numberEffectsRegistered++;
  }

  public void init(NEffectPanel panel) {
    thePanel = panel;
    for (Iterator<NEffect> it = internalEffects.iterator(); it.hasNext();) {
      NEffect theEffect = (NEffect) it.next();
      theEffect.init(panel);
    }
    //Enhancement 104
    setEffectElement(panel.getElement());
    initialized = true;
  }

  private void setParallelEffectLength(double seconds) {
    duration = seconds;
    for (Iterator<NEffect> it = internalEffects.iterator(); it.hasNext();) {
      NEffect theEffect = (NEffect) it.next();
      theEffect.setDuration(duration);
    }
  }

  public void initCompositeEffect() {
    initialized = true;
  }

  public ParallellCompositeEffect() {
    super();
  }
  
}