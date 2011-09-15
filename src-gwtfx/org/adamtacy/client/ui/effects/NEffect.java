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
import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.events.EffectInterruptedEvent;
import org.adamtacy.client.ui.effects.events.EffectInterruptedHandler;
import org.adamtacy.client.ui.effects.events.EffectPausedEvent;
import org.adamtacy.client.ui.effects.events.EffectPausedHandler;
import org.adamtacy.client.ui.effects.events.EffectResumedEvent;
import org.adamtacy.client.ui.effects.events.EffectResumedHandler;
import org.adamtacy.client.ui.effects.events.EffectStartingEvent;
import org.adamtacy.client.ui.effects.events.EffectStartingHandler;
import org.adamtacy.client.ui.effects.events.EffectSteppingEvent;
import org.adamtacy.client.ui.effects.events.EffectSteppingHandler;
import org.adamtacy.client.ui.effects.events.HasAllEffectEventHandlers;
import org.adamtacy.client.ui.effects.impl.browsers.EffectImplementation;
import org.adamtacy.client.ui.effects.transitionsphysics.EaseInOutTransitionPhysics;
import org.adamtacy.client.ui.effects.transitionsphysics.TransitionPhysics;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;


/**
 * A class that provides the basics of an effect.
 * 
 * From v4 a new style of setting properties is introduced.
 * 
 * From v3 of GWT-FX this class is based on the GWT Animation class to keep
 * divergence from GWT itself as minimal as possible. To maintain the previous
 * API, this class extends Animation with a number of fields and methods.
 * 
 * It is an abstract class that provides a fair amount of functionality, but
 * leaves the implementation of the
 * 
 * @author adam
 * 
 */
public abstract class NEffect extends Animation implements
    HasAllEffectEventHandlers {

  /**
   * Determine if an NEffect has started.
   * @return boolean value indicating if NEffect has started or not.
   */
  public boolean isStarted() {
    return effectStarted;
  }

  // Indicates if start > end in play parameters (different to inverse)
  protected boolean backwards = false;

  protected Vector<NEffect> chainedEffects;

  protected double currentEffectPosition = 0.0;
  protected double currentEffectPhysicalPosition = 0.0;

  protected final double DEFAULT_EFFECT_LENGTH = 2.0;

  // Removed by enhancement 104 in favour of a Vector effectElements
  //protected Element effectElement;
  protected Vector<Element> effectElements = new Vector<Element>();

  protected boolean effectFinished = false;
  
  protected boolean effectStarted = false;

  protected double effectLengthSeconds = DEFAULT_EFFECT_LENGTH;

  /**
   * Need this as we're not extending a widget here and so have no access to that HandlerManager.
   */
  HandlerManager handlerManager = new HandlerManager(DEFAULT_EFFECT_LENGTH);

  protected boolean interruptable = true;

  protected boolean inverted = false;

  /**
   * Details if the effect has been initialised yet or not.
   * Updated by either the init(NEffectPanel thePanel) method, or through the play() method.
   */
  protected boolean isInitialised = false;

  // Store the real effect end point - usually this will be at position 1.0
  // unless overridden by the user (see method interpolate for use of this variable).
  double requestedEffectEnd = 1.0;

  // Store the real effect start point - usually this will be at position 0.0
  // unless overridden by the user (see method interpolate for use of this variable).
  double requestedEffectStart = 0.0;

  Timer startDelay;

  /**
   * The effectPanel the effect may be attached to.
   * From v0.5 this is not necessary for all effects.
   */
  protected NEffectPanel thePanel;

  protected TransitionPhysics transition;

  public HandlerRegistration addEffectCompletedHandler(
      EffectCompletedHandler handler) {
    return addHandler(handler, EffectCompletedEvent.getType());
  }

  public HandlerRegistration addEffectInterruptedHandler(
      EffectInterruptedHandler handler) {
    return addHandler(handler, EffectInterruptedEvent.getType());
  }

  public HandlerRegistration addEffectStartingHandler(
      EffectStartingHandler handler) {
    return addHandler(handler, EffectStartingEvent.getType());
  }

  public HandlerRegistration addEffectSteppingHandler(
      EffectSteppingHandler handler) {
    return addHandler(handler, EffectSteppingEvent.getType());
  }
  
  public HandlerRegistration addEffectResumedHandler(
      EffectResumedHandler handler) {
    return addHandler(handler, EffectResumedEvent.getType());
  }
  
  public HandlerRegistration addEffectPausedHandler(
      EffectPausedHandler handler) {
    return addHandler(handler, EffectPausedEvent.getType());
  }
  
  /**
   * Adds an Element to be managed by this effect.
   * See enhancement 104: http://code.google.com/p/gwt-fx/issues/detail?id=104
   */
  public void addEffectElement(Element el){
    effectElements.add(el);
  }
  
  /**
   * Adds this handler to the widget.
   * 
   * @param handler the handler
   * @param type the event type
   * @return {@link HandlerRegistration} used to remove the handler
   */
  protected final <H extends EventHandler> HandlerRegistration addHandler(
      final H handler, GwtEvent.Type<H> type) {
    return ensureHandlers().addHandler(type, handler);
  }

  /**
   * Cancels the effect only if it is interruptable.
   */
  @Override
  public void cancel() {
      if (interruptable)
        super.cancel();
  }
  
  boolean paused = false;
  
  HandlerRegistration chainedEffectsHandlers;

  /**
   * Adds an effect to the chain. Effects are linked together in 
   * the init method.
   *  
   * @param effect The effect to chain.
   */
  public void chain(final NEffect effect) {
    if (chainedEffects == null){
      chainedEffects = new Vector<NEffect>();
    }
    chainedEffects.add(effect);
  }
  
  boolean looping = false;
  
  public void setLooping(boolean val) {
    looping = val;
  }
  
  public boolean isLooped(){
    return looping;
  }
  
  /**
   * Unchains all effects that have previously been chained.
   */
  public void unchain(){
    if(chainedEffects != null){
      if(chainedEffectsHandlers!=null)chainedEffectsHandlers.removeHandler();
      chainedEffects = null;
      chainedEffectsHandlers = null;
    }
    isInitialised = false;
  }

  /**
   * Ensures the existence of the handler manager.
   * 
   * @return the handler manager
   */
  HandlerManager ensureHandlers() {
    return handlerManager == null ? handlerManager = new HandlerManager(this)
        : handlerManager;
  }

  /**
   * Fires an event. Usually used when passing an event from one source to
   * another.
   * 
   * @param event the event
   */
  public void fireEvent(GwtEvent<?> event) {
    if (handlerManager != null) {
      handlerManager.fireEvent(event);
    }
  }

  /**
   * Returns the effect duration in seconds.
   */
  public double getDuration() {
    return effectLengthSeconds;
  }

  /**
   * Returns the first element this effect is applied to, unless this
   * effect is added to an {@link org.adamtacy.client.ui.NEffectPanel}, 
   * in which case that panel's Element is returned.
   */
  public Element getEffectElement(){
    Element toReturn = effectElements.get(0);
    if (thePanel!=null) toReturn = thePanel.getElement();
    return toReturn;
  }

  public NEffectPanel getEffectPanel() {
    return thePanel;
  }

  public HandlerManager getHandlers() {
    return handlerManager;
  }

  /**
   * Returns the current progress of this effect: a value somewhere 
   * between 0.0 and 1.0.
   */
  public double getProgress() {
    return currentEffectPhysicalPosition;
  }

  /**
   * Returns the interpolated progress of this effect.
   */
  public double getProgressInterpolated() {
    return currentEffectPosition;
  }

  /**
   * Returns the current Transition Physics Model used by this effect.
   */
  public TransitionPhysics getTransitionPhysics() {
    return transition;
  }

  public void init(){
    if (transition == null)
      transition = new EaseInOutTransitionPhysics();

    if (chainedEffects != null) {
      // Start chaining the effects together
      // First initialise them all
      if(thePanel!=null)
        for (Iterator<NEffect> it = chainedEffects.iterator(); it.hasNext();) {
          it.next().init(thePanel);
        }
      // Add an effect completed handler for this effect to fire all chained effects
      chainedEffectsHandlers = this.addEffectCompletedHandler(new EffectCompletedHandler() {
        public void onEffectCompleted(EffectCompletedEvent event) {
          for (Iterator<NEffect> it = chainedEffects.iterator(); it.hasNext();) {
            it.next().play();
          }
        }
      });
    }
    setUpEffect();
  }
    
  /**
   * Initialises an effect on an {@link org.adamtacy.client.ui.NEffectPanel}.
   * 
   * @param thePanel The NEffectPanel on which this effect is being applied.
   */
  public void init(NEffectPanel thePanel) {
    this.thePanel = thePanel;
    init();
    isInitialised = true;
  }
  
  /**
   * Interpolates the progress of this effect based on the Transition Physics. 
   * Subclasses should fire {@link org.adamtacy.client.ui.effects.events.EffectSteppingEvent} 
   * with the interpolated value.
   */
  @Override
  protected double interpolate(double progress) {
    currentEffectPhysicalPosition = interpolateWithoutUpdate(progress);
    return interpolateFinish(currentEffectPhysicalPosition);
  }
  
  protected double interpolateWithoutUpdate(double progress) {
    if (transition == null)
      return 1.0;
    if (!backwards) {
      progress = progress + requestedEffectStart;
      if (progress > requestedEffectEnd)
        progress = requestedEffectEnd;
    } else {
      progress = requestedEffectStart - progress;
      if (progress < requestedEffectEnd)
        progress = requestedEffectEnd;
    }
    if ((progress == 1.0)) {
      if (inverted)
        return 0.0;
      else
        return 1.0;
    }
    if (progress == 0.0) {
      if (inverted)
        return 1.0;
      else
        return 0.0;
    }
    return progress;
  }
  
  /**
   * Completes the interpolate functionality.
   * Zak asked what is returned if transition is null - transition shouldn't be null, but good catch.
   * If transition is null at this point, just return the value passed in. 
   * @param progress
   * @return
   */
  private double interpolateFinish(double progress){
    double val = progress;
    if (transition!=null){
    	val = transition.getAnimationPosition(progress);
        if (inverted)
            val = 1 - val;
    }
    return val;
  }

  /**
   * Inverts an effect. Currently not available for Sequential composite effects.
   */
  public void invert() {
    assert (!(this instanceof SequentialCompositeEffect));
    inverted = !inverted;
  }
  
  /**
   * Returns a boolean value indicating if the effect has finished.
   */
  public boolean isFinished() {
    return effectFinished;
  }

  /**
   * Returns a boolean value to indicate if the effect is interruptable.
   */
  public boolean isInterruptable() {
    return interruptable;
  }

  /**
   * Returns a boolean value to indicate if the effect is inverted.
   */
  public boolean isInverted() {
    return inverted;
  }

  /**
   * Called immediately after the animation is cancelled. Fires 
   * {@link org.adamtacy.client.ui.effects.events.EffectPausedEvent} or
   * {@link org.adamtacy.client.ui.effects.events.EffectInterruptedEvent} 
   * as appropriate.
   * 
   * Does not call <code>super.onCancel()</code>. See issues 82 and 83:
   * http://code.google.com/p/gwt-fx/issues/detail?id=82
   * http://code.google.com/p/gwt-fx/issues/detail?id=83
   */
  @Override
  protected void onCancel() {
    if(paused) fireEvent(new EffectPausedEvent());
    else fireEvent(new EffectInterruptedEvent());
  }

  /**
   * Called immediately after the animation completes. Fires 
   * {@link org.adamtacy.client.ui.effects.events.EffectCompletedEvent}. 
   * If this effect is looping, calls {@link #play(double, double)}.
   */
  @Override
  protected void onComplete() {
    super.onComplete();
    effectFinished = true;
    fireEvent(new EffectCompletedEvent());
    if(looping){
      DeferredCommand.addCommand(new Command(){
        public void execute() {
          play(0.0, 1.0);          
        }
      });
    }
  }

  /**
   * Called immediately before the animation starts. Fires 
   * {@link org.adamtacy.client.ui.effects.events.EffectStartingEvent}.
   * 
   * Does not call <code>super.onStart()</code>. See issue 95:
   * http://code.google.com/p/gwt-fx/issues/detail?id=95
   */
  @Override
  protected void onStart() {
    effectFinished = false;
    effectStarted = true;
    fireEvent(new EffectStartingEvent());
  }

  /**
   * Called when the animation should be updated.
   * 
   * The value of progress is between 0.0 and 1.0 inclusively (unless you
   * override the {@link #interpolate(double)} method to provide a wider range
   * of values). You can override {@link #onStart()} and {@link #onComplete()}
   * to perform setup and tear down procedures.
   * 
   * Note that onUpdate events are now triggered by a call to the interpolate
   * method whereas before they were through the direct update methods - this
   * needs to be done since the update method in GWT Animation is private.
   */
  @Override
  protected void onUpdate(double progress) {
    currentEffectPosition = progress;
  }
  
  /**
   * Pauses an effect and sets paused variable to true
   */
  public void pause(){
    cancel();
    paused = true;
  }

  /**
   * Plays the effect after a specified delay, starting at position 0.0 
   * and going to 1.0.
   * 
   * @param afterDelay The delay in milliseconds.
   */
  public void play(int afterDelay){
      startDelay = new Timer(){
        @Override
        public void run() {
          play();
        }
      };
      startDelay.schedule(afterDelay);
  }
  
  /**
   * Plays the effect after a specified delay from specified start and end 
   * positions.
   * 
   * @param start Start position.
   * @param end End position.
   * @param afterDelay The delay in milliseconds.
   */
  public void play(final double start, final double end, int afterDelay){
      startDelay = new Timer(){
        @Override
        public void run() {
          play(start, end);
        }
      };
      startDelay.schedule(afterDelay);
  }
  
  /**
   * Plays the effect from specified start and end positions. If the start position 
   * is greater than the end position, this effect is played in reverse.
   * 
   * Note that the portion of effect selected will run at the same tempo as the
   * whole effect, but the firing of any postEffect handlers will not occur
   * until the whole duration is complete.
   * 
   * For example, an effect with duration 2 seconds, but calling play(0.0, 0.5)
   * will appear to viewer to complete in 1 second, but will not fire any
   * postEvents until after 2 seconds.
   * 
   * @param start The requested start position.
   * @param end The requested end position.
   */
  public void play(double start, double end) {
    if (start > end)
      backwards = true;
    else
      backwards = false;
    requestedEffectStart = start;
    requestedEffectEnd = end;
    play();
  }
  
  /**
   * Plays the effect using the registered start and end positions. If no positions 
   * are set they are defaulted to 0.0 and 1.0 respectively.
   */
  public void play() {
    if(!isInitialised){
      init();
      isInitialised = true;
    }
    double timeRatio = this.requestedEffectEnd - this.requestedEffectStart;
    if (timeRatio<0) timeRatio = -timeRatio;
    run((int) ((effectLengthSeconds * 1000) * timeRatio));
  }
  
  protected void registerEffectElement(){
    if(effectElements.isEmpty()){
      Element el = thePanel.getWidget().getElement();
      effectElements.add(el);
    }
  }
  
  /**
   * Cancels this effect and tears it down.
   */
  public void remove() {
    cancel();
    tearDownEffect();
  }

  /**
   * Removes and tears down this effect. This should only be used when this 
   * effect has been added to an Element directly. If this effect is added to 
   * an {@link org.adamtacy.client.ui.NEffectPanel}, it must be removed using 
   * {@link org.adamtacy.client.ui.NEffectPanel#removeEffect(NEffect)}.
   * 
   * See issue 77: http://code.google.com/p/gwt-fx/issues/detail?id=77
   */
  public void removeEffect(){
    removeEffect(true);
  }

  /**
   * Removes this effect, optionally tearing it down. This should only be used 
   * when this effect has been added to an Element directly. If this effect is 
   * added to an {@link org.adamtacy.client.ui.NEffectPanel}, it must be removed 
   * using {@link org.adamtacy.client.ui.NEffectPanel#removeEffect(NEffect)}.
   * 
   * See issue 77: http://code.google.com/p/gwt-fx/issues/detail?id=77
   * 
   * @param undoEffect Whether or not to tear down this effect.
   */
  public void removeEffect(boolean undoEffect){
    assert(thePanel == null);
    if(undoEffect) tearDownEffect();
    effectElements = null;
  }

  /** 
   * Resets this effect. 
   */
  // FIXME adam.tacy Shouldn't this work even if the panel is null?
  public void reset() {
    if (thePanel != null)
      onUpdate(0.0);
  }

  /**
   * Plays this effect backwards from its current progress to 0.0. Fires 
   * {@link org.adamtacy.client.ui.effects.events.EffectResumedEvent}.
   */
  public void resumeBackwards() {
	resumeBackwards(0.0);
  }

  /**
   * Plays this effect backwards from its current progress to a specified
   * point. Fires {@link org.adamtacy.client.ui.effects.events.EffectResumedEvent}.
   * 
   * @param end Progress point at which to end.
   */
  public void resumeBackwards(double end) {
    paused = false;
    play(getProgress(), end);
    fireEvent(new EffectResumedEvent());
  }

  /**
   * Plays this effect from its current progress to 1.0. Fires 
   * {@link org.adamtacy.client.ui.effects.events.EffectResumedEvent}.
   */
  public void resumeForwards() {
	resumeForwards(1.0);
  }

  /**
   * Plays this effect from its current progress to a specified point. 
   * Fires {@link org.adamtacy.client.ui.effects.events.EffectResumedEvent}.
   * 
   * @param end Progress point at which to end.
   */
  public void resumeForwards(double end) {
    paused = false;
    this.play(getProgress(), end);
    fireEvent(new EffectResumedEvent());
  }

  /**
   * Sets this effect's duration in seconds.
   * 
   * @param duration The duration in seconds.
   */
  public void setDuration(double duration) {
    effectLengthSeconds = duration;
  }
  
  /**
   * Sets the Element to which this effect will be applied. This will remove
   * all ties this effect has to other Elements. If you would like to add 
   * multiple Elements, use {@link #addEffectElement(Element)}.
   * 
   * @param element The Element to which this effect should be applied.
   */
  public void setEffectElement(Element element){
    if (thePanel!=null)throw new RuntimeException("Trying to change effectElement when an EffectPanel is in place");
    effectElements = new Vector<Element>();
    effectElements.add(element);
    isInitialised = false;
  }

  /**
   * Sets the effect's position. Fires appropriate events.
   * 
   * @param position The position to be set.
   */
  public void setPosition(double position) {
    if (!this.isInitialised) {
      init();
      isInitialised = true;
    }
    double val = interpolate(position);
    onUpdate(val);
    if (val == 0) fireEvent(new EffectStartingEvent());  
    else if (val == 0) fireEvent(new EffectCompletedEvent());
    else fireEvent(new EffectSteppingEvent());
  }
  
  /**
   * Sets the {@link org.adamtacy.client.ui.effects.transitionsphysics.TransitionPhysics} 
   * to be used with this effect.
   * 
   * @param transition The transition physics to use.
   */
  public void setTransitionType(TransitionPhysics transition) {
    this.transition = transition;
    if(chainedEffects!=null){
      for (Iterator<NEffect> it = chainedEffects.iterator(); it.hasNext();) {
        NEffect theEffect = it.next();
        if(!theEffect.equals(this))theEffect.setTransitionType(transition);
      }
    }
  }
  
  /**
   * Gets the layout definition that IE requires to use filters.
   * Only has meaning for IE.
   */
  public String getIELayoutDefinition(){
    return EffectImplementation.getIELayoutDefinition();
  }
  
  /**
   * Sets the layout definition that IE requires to use filters.
   * Only required for IE.
   */
  public void setIELayoutDefinition(String id, String val){
    EffectImplementation.setIELayoutDefinition(id,val);
  }
  
  /**
   * Sets up this effect. Implemented by subclasses, for example a Show effect 
   * may wish to hide the Element before starting.
   */
  public abstract void setUpEffect();
  
  /**
   * Tears down this effect. Called when this effect is removed. Implemented by 
   * subclasses, for example, the Move effect needs to fix the style properties 
   * that it sets up.
   */
  public abstract void tearDownEffect();
}
