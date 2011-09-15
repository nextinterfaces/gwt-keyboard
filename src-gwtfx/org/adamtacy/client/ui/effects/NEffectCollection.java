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
import java.util.Vector;

import org.adamtacy.client.ui.NEffectPanel;
import org.adamtacy.client.ui.effects.transitionsphysics.TransitionPhysics;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

/**
 * A collection of effects.
 */
@SuppressWarnings("serial")
public class NEffectCollection extends Vector<NEffect> {

  /**
   * Inverts the effects.
   */
  public void invertEffects() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().invert();
  }

  /**
   * Plays the effects from their default start position to default end position.
   */
  public void playEffects() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().play();
  }

  /**
   * Plays the effects from specified start position to specified end position.
   * 
   * @param start The start position.
   * @param end The end position.
   */
  public void playEffects(double start, double end) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().play(start, end);
  }
  
  /**
   * Plays all effects after a specified delay.
   * 
   * @param delay The delay in milliseconds.
   */
  public void playEffects(int delay) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().play(delay);    
  }

  /**
   * Plays all effects from specified start and end positions after
   *  a specified delay.
   * 
   * @param start The start position.
   * @param end The end position.
   * @param delay The delay in milliseconds.
   */
  public void playEffects(double start, double end, int afterDelay) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().play(start,end,afterDelay);    
  }

  /**
   * Removes all effects.
   */
  public void removeEffects() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().remove();
  }

  /**
   * Resumes all effects backwards to the default end position.
   */
  public void resumeEffectsBackwards() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().resumeBackwards();
  }

  /**
   * Resumes all effects backwards to a specified end position.
   * 
   * @param end The end position.
   */
  public void resumeEffectsBackwards(double end) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().resumeBackwards(end);
  }
  
  /**
   * Resets all effects.
   */
  public void resetEffects() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().reset();
  }

  /**
   * Resumes all effects to the default end position.
   */
  public void resumeEffectsForwards() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().resumeForwards();
  }
  
  /**
   * Resumes all effects to a specified end position.
   * 
   * @param end The end position.
   */
  public void resumeEffectsForwards(double end) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().resumeForwards(end);
  }

  /**
   * Sets each effects' duration to a specified number of seconds. 
   * 
   * @param duration The duration in seconds.
   */
  public void setEffectsLength(double duration) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();) {
      NEffect theEffect = it.next();
      if (theEffect instanceof ParallellCompositeEffect)
        ((ParallellCompositeEffect) theEffect).setDuration(duration);
      else
        theEffect.setDuration(duration);
    }
  }
  
  
  /**
   * Sets each effects' position. 
   * 
   * @param position The position.
   */
  public void setPosition(double position) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().setPosition(position);
  }

  /**
   * Sets each effects' transition physics. 
   * 
   * @param transition The transition physics.
   */
  public void setTransitionTypes(TransitionPhysics transition) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();) {
      NEffect theEffect = it.next();
      if (theEffect instanceof ParallellCompositeEffect)
        ((ParallellCompositeEffect) theEffect).setTransitionType(transition);
      else
        theEffect.setTransitionType(transition);
    }
  }

  /**
   * Sets up all effects registered with the given 
   * {@link org.adamtacy.client.ui.NEffectPanel}.
   * 
   * @param panel The panel.
   */
  public void setUpEffects(final NEffectPanel panel) {
    for (Iterator<NEffect> it = iterator(); it.hasNext();) {
      final NEffect theEffect = it.next();
      if (theEffect instanceof ParallellCompositeEffect)
        ((ParallellCompositeEffect) theEffect).init(panel);
      else {
        if (theEffect instanceof RequiresDimensions){
          DeferredCommand.addCommand(new Command(){
            public void execute() {
              panel.getPanelWidget().setVisible(true); 
              theEffect.init(panel);
            }
          });
        }
        else {
          theEffect.init(panel);
          panel.getPanelWidget().setVisible(true); 
        }
      }
    }
  }

  /**
   * Stops all effects.
   */
  public void stopEffects() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();)
      it.next().cancel();
  }

  /**
   * Tears down all effects.
   */
  public void tearDownEffects() {
    for (Iterator<NEffect> it = iterator(); it.hasNext();) {
      NEffect theEffect = it.next();
      if (theEffect instanceof ParallellCompositeEffect)
        ((ParallellCompositeEffect) theEffect).tearDownEffect();
      else
        theEffect.tearDownEffect();
    }
  }
}