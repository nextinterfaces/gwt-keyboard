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
import org.adamtacy.client.ui.effects.NEffectCollection;
import org.adamtacy.client.ui.effects.ParallellCompositeEffect;

import org.adamtacy.client.ui.effects.exception.NEffectNotInterruptableException;
import org.adamtacy.client.ui.effects.transitionsphysics.TransitionPhysics;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * An effect panel that can be assigned numerous effects; the effects can then
 * be played, finished and interrupted (as well as resuming from a particular
 * position and going forwards or backwards).
 * 
 * Note that it is a SimplePanel and can therefore contain only one widget -
 * though of course, that widget could be a composite or a panel with various
 * other widgets/panels associated with it.
 * 
 */
public class NEffectPanel extends SimplePanel implements HasEffects {
  
  private boolean preventDefaultEvent = true;
  
  public boolean isPreventDefaultEvent() {
    return preventDefaultEvent;
  }

  public void setPreventDefaultEvent(boolean preventDefaultEvent) {
    this.preventDefaultEvent = preventDefaultEvent;
  }

  public void onBrowserEvent(Event event){
    if (preventDefaultEvent) DOM.eventPreventDefault(event);
    super.onBrowserEvent(event);
  }

  TransitionPhysics defaultTransition;

  NEffectCollection registeredEffects = null;

  public NEffectPanel() {
    super();
    this.setStyleName("effectPanel");
    // Quick fix here for IE (for such a small thing it is not worth trying to
    // use deferred binding....)
    DOM.setStyleAttribute(getElement(), "zoom", "1");
    getElement().setPropertyInt("zoom", 1);
  }

  @Override
  public void add(Widget w) {
    super.add(w);
    // If the panel is attached then we want to immediately set the dimensions
    // of the effect panel to
    // the size of the widget.
    // Note that this means as effects are played we do not alter the
    // positioning of any elements around this panel,
    // We need to try and ensure the panel and content are added into the DOM
    // first, otherwise we could get some
    // strange (wrong values) results, so wrap up in a DeferredCommand.
    if (isAttached()) {
      DeferredCommand.addCommand(new Command() {
        public void execute() {
          setPanelDimensions();
        }
      });
      // Initially hide the panel, allowing for any set-up required to be applied;
      // see onAttach() method
      // for where the panel is finally set to be visible - if not one, then effect
      // such as Show could
      // result in a flickering effect as the DOM is added to the browser to then
      // have visibility set to 0.
      w.setVisible(false);
    }
  }

  /**
   * Adds an effect to the panel. When the panel's effects are requested
   * to play, this effect will be included in the playlist.
   */
  public void addEffect(NEffect theEffect) {
    if (registeredEffects == null)
      registeredEffects = new NEffectCollection();
    registeredEffects.add(theEffect);

    if (defaultTransition != null)
      theEffect.setTransitionType(defaultTransition);

    // If the panel is already attached, then initialise the effect, if it is
    // not attached, then we save some application start-up time by deferring 
    // the initialisation until the onAttach() method.
    if (this.isAttached()) {
      registeredEffects.setUpEffects(this);
    }
  }

  /**
   * Cancels all effects on the panel (assuming it is attached).
   */
  public void cancelEffects() throws NEffectNotInterruptableException {
    if ((registeredEffects != null) && (this.isAttached()))
      registeredEffects.stopEffects();
  };

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#getPanelWidget()
   */
  public Widget getPanelWidget() {
    return this.getWidget();
  };

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#invertEffects()
   */
  public void invertEffects() {
    if (registeredEffects != null)
      registeredEffects.invertEffects();
  }
  
  @Override
  public void onLoad() {
    super.onLoad();
    if ((registeredEffects != null))
      registeredEffects.setUpEffects(this);
    // Ensure there is a widget to deal with before dimensioning,  
    // otherwise deal with this when widget is added.
    if (this.getPanelWidget()!=null){
      DeferredCommand.addCommand(new Command(){
      public void execute(){
        setPanelDimensions();
      }
    });
    }
  }
  

  /**
   * Stops all effects and tidies up
   */
  @Override
  public void onUnload() {
    try {
      if (registeredEffects != null)
        this.cancelEffects();
    } catch (NEffectNotInterruptableException e) {
      e.printStackTrace();
    }
    registeredEffects = null;
    super.onUnload();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#playEffects()
   */
  public void playEffects() {
    if ((registeredEffects != null) && (this.isAttached())) {
      registeredEffects.playEffects();
    }
  }
  
  public void playEffects(int afterDelay) {
    if ((registeredEffects != null) && (this.isAttached())) {
      registeredEffects.playEffects(afterDelay);
    }
  }
  
  public void playEffects(double start, double end, int afterDelay) {
    if ((registeredEffects != null) && (this.isAttached())) {
      registeredEffects.playEffects(start, end, afterDelay);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#playEffects(double, double)
   */
  public void playEffects(double start, double end) {
    if ((registeredEffects != null) && (this.isAttached()))
      registeredEffects.playEffects(start, end);
  };

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.adamtacy.client.ui.HasEffects#removeEffect(org.adamtacy.client.ui.effects
   * .NEffect)
   */
  public void removeEffect(NEffect theEffect) {
    if (registeredEffects != null) {
      // If the Panel is attached, then clean up the effect using it's
      // tearDownEffect method.
      if (this.isAttached()) {
        //if (theEffect instanceof SequentialCompositeEffect)   
        //((SequentialCompositeEffect) theEffect).remove();
        //else 
        if (theEffect instanceof ParallellCompositeEffect)
          ((ParallellCompositeEffect) theEffect).remove();
        else
          theEffect.remove();
      }
      registeredEffects.remove(theEffect);
    }
  };

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.adamtacy.client.ui.HasEffects#removeEffect(org.adamtacy.client.ui.effects
   * .NEffect)
   */
  public void removeEffects() {
    if (registeredEffects != null) {
      registeredEffects.removeEffects();
      // Issue #50 : added "registeredEffects = null" otherwise effects are not really removed.  The call to 
      // NEffectCollection removeEffects() correctly calls remove on each effect (which tidies the effect up)
      // but does not remove it from the collection, meaning the effect is still attached.
      registeredEffects = null;
    }
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * org.adamtacy.client.ui.HasEffects#removeEffect(org.adamtacy.client.ui.effects
   * .NEffect)
   */
  public void resetEffects() {
    if (registeredEffects != null) {
      registeredEffects.resetEffects();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#resumeEffectsBackwards()
   */
  public void resumeEffectsBackwards() {
    if (registeredEffects != null)
      registeredEffects.resumeEffectsBackwards();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#resumeEffectsForwards()
   */
  public void resumeEffectsForwards() {
    if (registeredEffects != null)
      registeredEffects.resumeEffectsForwards();
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#resumeEffectsBackwards()
   */
  public void resumeEffectsBackwards(double end) {
    if (registeredEffects != null)
      registeredEffects.resumeEffectsBackwards(end);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#resumeEffectsForwards()
   */
  public void resumeEffectsForwards(double end) {
    if (registeredEffects != null)
      registeredEffects.resumeEffectsForwards(end);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.adamtacy.client.ui.HasEffects#setEffectsLength(double)
   */
  public void setEffectsLength(double duration) {
    if (registeredEffects != null)
      registeredEffects.setEffectsLength(duration);
  }
  
  /**
   * Allows external user to specify position within an effect.
   * 
   * @param progress the position to be set.
   */
  public void setPosition(double position) {
    if (registeredEffects != null)
      registeredEffects.setPosition(position);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see
   * org.adamtacy.client.ui.HasEffects#setEffectsTransitionType(org.adamtacy
   * .client.ui.effects.transitionsphysics.TransitionPhysics)
   */
  public void setEffectsTransitionType(TransitionPhysics newTransition) {
    defaultTransition = newTransition;
    if (registeredEffects != null)
      registeredEffects.setTransitionTypes(newTransition);
  }
  
  /**
   * Uses browser DOM return values to address issue 59 
   * (http://code.google.com/p/gwt-fx/issues/detail?id=59).
   */
  protected void setPanelDimensions() {
//    this.setHeight(getPanelWidget().getOffsetHeight() + "px");
//    this.setWidth(getPanelWidget().getOffsetWidth() + "px");
//    GWT.log("Width: "+temp.getComputedStyle(getPanelWidget().getElement(), "width"),null);
//    GWT.log("Height: "+temp.getComputedStyle(getPanelWidget().getElement(), "height"),null);

    DOM.setStyleAttribute(this.getElement(), "width", ""+DOM.getStyleAttribute(getPanelWidget().getElement(), "width"));
    DOM.setStyleAttribute(this.getElement(), "height", ""+DOM.getStyleAttribute(getPanelWidget().getElement(), "height"));
  }

}