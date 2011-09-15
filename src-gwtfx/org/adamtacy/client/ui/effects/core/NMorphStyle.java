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
package org.adamtacy.client.ui.effects.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.adamtacy.client.ui.effects.NEffect;
import org.adamtacy.client.ui.effects.events.EffectSteppingEvent;
import org.adamtacy.client.ui.effects.impl.css.StyleImplementation;
import org.adamtacy.client.ui.effects.impl.css.Rule;
import org.adamtacy.client.ui.effects.impl.css.Property;
import org.adamtacy.client.ui.effects.impl.css.Selector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;

/**
 * Class that represents a morphing between two styles, e.g.
 * 
 * start {top: 0px; left: 0px;} -> end {top: 100px; left: 100px;}
 * 
 * @author adam
 * 
 */
public class NMorphStyle extends NEffect {

  /**
   * Inner class representing a pair of properties start can be null, but the
   * end should not be
   * 
   * @author adam
   * 
   */
  protected class PropertyPair {
    protected Property start, end;

    public PropertyPair(Property start, Property end) {
      this.start = start;
      this.end = end;
      if (end == null)
        throw new RuntimeException(
            "End value of a property pair shold not be null");
    }

    public Property getEnd() {
      return end;
    }

    public Property getStart() {
      return start;
    }
  }

  /**
   * Set of effects that it has been determined make up the morph from the start
   * to the end styles supplied in the constructor.
   */
  protected HashMap<String, ChangeInterface> determinedEffects;

  /**
   * Set of property pairs representing the valid start/end pairs of properties
   * the effect will work over.
   */
  protected Vector<PropertyPair> propertyPairs;

  /**
   * Position at which all binary effects will switch state.
   */
  private double switchFrameNumber = 0.5;

  /**
   * The start and end CSS rules
   */
  protected Rule theStart, theEnd;

  public void addEffectElement(Element el) {
    super.addEffectElement(el);
    effectElement = effectElements.get(0);
  }

  public void setEffectElement(Element el) {
    super.setEffectElement(el);
    effectElement = effectElements.get(0);
  }

  /**
   * Simple constructor
   */
  public NMorphStyle() {
  }

  /**
   * Construct effect to be applied to a specific DOM element
   * 
   * @param el
   */
  public NMorphStyle(Element el) {
    addEffectElement(el);
  }

  /**
   * Constructor to morph a single Property between two values
   * 
   * @param start Initial property.
   * @param end Expected end property.
   */
  public NMorphStyle(Property start, Property end) {
    if (!start.getName().equals(end.getName()))
      throw new RuntimeException(
          "Start and End properties must be of the same type!  You used start="
              + start.getName() + " and end=" + end.getName());
    // Added variable initialiser - see Issue 110.
    propertyPairs = new Vector<PropertyPair>();
    propertyPairs.add(new PropertyPair(start, end));
    // Updated to store the property as a rule, see Issue 115
    theStart = new Rule("s{"+start+"}");
    theEnd = new Rule("s{"+end+"}");
  }

  /**
   * Constructor to morph between two style rules
   * 
   * @param start Initial style rule.
   * @param end Expected end style rule.
   */
  public NMorphStyle(Rule start, Rule end) {
    theStart = start;
    theEnd = end;
    registerProperties(start, end, null, null);
  }

  public NMorphStyle(Element el, Rule start, Rule end) {
    addEffectElement(el);
    theStart = start;
    theEnd = end;
    registerProperties(start, end, null, null);
  }

  /**
   * Constructor to morph between two style rules defined in an external style
   * sheet.
   * 
   * @param start Initial style rule selector.
   * @param end Expected end style rule selector.
   */
  public NMorphStyle(final Selector start, final Selector end) {
    theStart = start.getRuleDefinition();
    theEnd = end.getRuleDefinition();
    registerProperties(theStart, theEnd, null, null);
  }

  public NMorphStyle(Element el, final Selector start, final Rule end) {
    addEffectElement(el);
    theStart = start.getRuleDefinition();
    theEnd = end;
    registerProperties(theStart, theEnd, null, null);
  }

  public NMorphStyle(Element el, final Rule start, final Selector end) {
    addEffectElement(el);
    theStart = start;
    theEnd = end.getRuleDefinition();
    registerProperties(theStart, theEnd, null, null);
  }

  /**
   * Get the position at which binary effecys will switch status.
   * 
   * @return
   */
  public double getSwitchFrameNumber() {
    return switchFrameNumber;
  }

  /**
   * Update the effect by updating all the effects held in the determinedEffects
   * HashMap
   */
  @Override
  protected void onUpdate(double progress) {
    StringBuffer sb = new StringBuffer();
    
    super.onUpdate(progress);
    for (Iterator<String> it = determinedEffects.keySet().iterator(); it.hasNext();) {
      String theChange = it.next();
      ChangeInterface currChange = determinedEffects.get(theChange);
      sb.append(currChange.performStep(effectElement, theChange, progress));
      sb.append(";");
    }
    fireEvent(new EffectSteppingEvent(progress, sb.toString()));
  }

  /**
   * Creates a list of properties that we are interested in mapping between. In
   * the worst case, this is all properties provided in the rules, however there
   * are two spcial cases:
   * 
   * 1. Items that appear in the start rule but not in the end rule. The
   * understanding here is that this property will not change over the
   * progression, so we can ignore it. 2. Items that appear in the end rule but
   * not in the start rule. The understanding here is that we need to progress
   * from some as yet undefined state to the end state. When the effect is set
   * up, an attempt is made to get the computed style for the start state
   * (failing if the start style is determine to be "auto" since how is that to
   * be interpreted.
   * 
   * @param start
   * @param end
   */
  protected void registerProperties(Rule start, Rule end, Property replaceStart, Property replaceEnd) {
    propertyPairs = new Vector<PropertyPair>();
    Property entryStart;
    Property entryEnd;
    boolean replacedStart = false;
    boolean replacedEnd = false;
    // Since we are interested in end properties, iterate over them.
    for (Iterator<String> it = end.getProperties().keySet().iterator(); it.hasNext();) {
      // Get the property name
      String name = it.next();
      // See if we are trying to replace this from a setNew[Start|End]Property call
      if ((replaceStart!=null)&&(replaceStart.getName().equals(name))){
        entryStart = replaceStart;
        replacedStart = true;
      }
      // Find the entry in the start rule - this could be null.
      else entryStart = start.getProperties().get(name);
      // See if we are trying to replace this from a setNew[Start|End]Property call
      if ((replaceEnd!=null)&&(replaceEnd.getName().equals(name))){
        entryEnd = replaceEnd;
        replacedEnd = true;
      }
      // Find the entry in the end rule - this can not be null.
      else entryEnd = end.getProperties().get(name);
      // Create and record a property pair
      propertyPairs.add(new PropertyPair(entryStart, entryEnd));
      // Remove the "name" key from list of start elements
      start.getProperties().remove(name);
    }
    // Now look at the remaining start properties and add them with no change to
    // be made
    for (Iterator<String> it = start.getProperties().keySet().iterator(); it.hasNext();) {
      // Get the property name
      String name = it.next();
      // See if we are trying to replace this from a setNew[Start|End]Property call
      if ((replaceStart!=null)&&(replaceStart.getName().equals(name))) {
        entryStart = replaceStart;
        replacedStart = true;
      }
      // Find the entry in the start rule - this cannot be null (otherwise it would still be in the list....).
      else entryStart = start.getProperties().get(name);
      // Do same for the end property - if in replaceEnd, replace it, otherwise set it to the entryStart value.
      if ((replaceEnd!=null)&&(replaceEnd.getName().equals(name))) {
        entryEnd = replaceEnd;
        replacedEnd = true;
      }
      else entryEnd = entryStart;
      // At this point if replacedStart or replacedEnd are still false and replaceStart or replaceEnd are not null then we need to add
      if ((replaceEnd!=null)&&(!replacedEnd)){
        entryStart = null;
        entryEnd = replaceEnd;
        theEnd.addProperty(replaceEnd);
      }
      if ((replaceStart!=null)&&(!replacedStart)){
        entryStart = replaceStart;
        entryEnd = replaceEnd;
        theEnd.addProperty(replaceEnd);
        theStart.addProperty(replaceStart);
      }
      propertyPairs.add(new PropertyPair(entryStart, entryEnd));
    }
  }


  /**
   * Set the new end style for the effect, and if the effectElement is not null,
   * set up the effect again.
   * 
   * @param end New CSS Rule for end of the effect.
   */
  public void setNewEndStyle(Rule end) {
    registerProperties(theStart, end, null, null);
    theEnd = end;
    if (!effectElements.isEmpty())
      setUpEffect();
  }
  
  /**
   * Set the new end Property for the effect, and if the effectElement is not null,
   * set up the effect again.
   * 
   * If endProp already exists in the registered end rule for this effect, then it replaces the original value,
   * otherwise it is added as a new property.
   * 
   * @param end New CSS Rule for end of the effect.
   */
  public void setNewEndProperty(Property endProp) {
    registerProperties(theStart, theEnd, null, endProp);
    if (!effectElements.isEmpty())
      setUpEffect();
  }
  
  /**
   * Set the new start Property for the effect, and if the effectElement is not null,
   * set up the effect again.
   * 
   * If endProp already exists in the registered end rule for this effect, then it replaces the original value,
   * otherwise it is added as a new property.
   * 
   * @param end New CSS Rule for end of the effect.
   */
  public void setNewStartProperty(Property startProp) {
    registerProperties(theStart, theEnd, startProp, null);
    if (!effectElements.isEmpty())
      setUpEffect();
  }

  /**
   * Set the new start style for the effect, and if the effectElement is not
   * null, set up the effect again.
   * 
   * @param start New CSS Rule for start of the effect.
   */
  public void setNewStartStyle(Rule start) {
    registerProperties(start, theEnd, null, null);
    theStart = start;
    if (!effectElements.isEmpty())
      setUpEffect();
  }

  /**
   * Set the new start and end styles for the effect, and if the effectElement
   * is not null, set up the effect again.
   * 
   * @param start New CSS Rule for start of the effect.
   * @param end New CSS Rule for end of the effect.
   */
  public void setNewStyles(Rule start, Rule end) {
    registerProperties(start, end, null, null);
    theStart = start;
    theEnd = end;
    if (!effectElements.isEmpty())
      setUpEffect();
  }

  /**
   * Set the position at which any determined binary effects will switch style.
   * 
   * @param switchFrameNumber
   */
  public void setSwitchFrameNumber(double switchFrameNumber) {
    this.switchFrameNumber = switchFrameNumber;
  }

  protected Element effectElement;

  /**
   * Sets up the morph effect. Take the previously calculated set of property
   * pairs and for each one ensure there are valid start and end values (which
   * might involve calculating the start value from the attached element - which
   * is why this step is done in this method rather than in the constructor.
   * 
   * With start and end values found, determine and register the appropriate
   * Change object to use (ie scalar, binary, color, clip) to inact the morph
   * for this property.
   * 
   */
  @Override
  public void setUpEffect() {
    // return if we have already done this (identifiable if property pairs is
    // null)
    if (propertyPairs == null)
      return;

    registerEffectElement();
    if (effectElement == null)
      effectElement = (Element) effectElements.get(0);

    determinedEffects = new HashMap<String, ChangeInterface>();

    // Iterate over the property pairs to ensure values are set.
    for (Iterator<PropertyPair> it = propertyPairs.iterator(); it.hasNext();) {
      try {
        PropertyPair theMorph = it.next();
        String styleName = theMorph.end.getName();
        Property start = theMorph.start;
        Property end = theMorph.end;
        if (start == null) {
          // Attempt to get value from the DOM
          start = new Property(styleName + ":"
              + StyleImplementation.getComputedStyle(effectElement, styleName));
        }
        // Now determine that type of change that is required.
        ChangeInterface theChange;
        if (StyleImplementation.isColour(start.toString())) {
          // It's a colour change
          theChange = new NChangeColorAction(start.getValue(), end.getValue());
          determinedEffects.put(styleName, theChange);
          theChange.setUp(effectElement, styleName, switchFrameNumber);
        } else if (StyleImplementation.isClip(start.toString())) {
          // It's a clip change
          // Order of check moved due to Issue #108
          theChange = new NChangeClipAction(start.getUnitizedValue(),
              end.getUnitizedValue());
          determinedEffects.put(styleName, theChange);
          theChange.setUp(effectElement, styleName, switchFrameNumber);
        } else if (StyleImplementation.isScalar(start.toString(),
            start.getUnitizedValue())) {
          // It's a scalar change
          theChange = new NChangeScalarAction(start.getValue()
              + start.getUnits(), end.getValue() + end.getUnits());
          determinedEffects.put(styleName, theChange);
          theChange.setUp(effectElement, styleName, switchFrameNumber);
        } else {
          // Assume its a binary style
          GWT.log(
              "GWT-FX: Assuming that "
                  + start.getName()
                  + " is a style attribute that switches as a binary (have you forgotten to include the unit (px/pt/em etc)?",
              null);
          theChange = new NChangeBinaryAction(start.getValue(), end.getValue());
          determinedEffects.put(styleName, theChange);
          theChange.setUp(effectElement, styleName, switchFrameNumber);
        }
      } catch (RuntimeException e) {
        GWT.log("MorphEffect CSS Style Exception", e);
      }
    }
    // Finally call reset() which applies the start style to the element.
    reset();
    // and we can get rid of the property pairs element since it has now done
    // its job.
    propertyPairs = null;

    // Assign the start style to the element
    // CAUSED AN ERROR WITH IE (surprisingly) where the
    // getElementAttribute() method in GWT seems to not
    // return back a String.
    //
    // Would appear to be fixed in GWT 1.5 (release) :
    // http://groups.google.com/group/Google-Web-Toolkit-Contributors/browse_thread/thread/c708f46d592bcad8#
    //
    // if(GWT.isScript()){
    // DOM.setElementAttribute((com.google.gwt.user.client.Element)effectElement,
    // "style", DOM.getElementAttribute(
    // (com.google.gwt.user.client.Element)effectElement, "style") +
    // theStart.getDefinition());
    // }
    //    

  }

  @Override
  public void tearDownEffect() {
    for (Iterator<String> it = determinedEffects.keySet().iterator(); it.hasNext();) {
      ChangeInterface currChange = determinedEffects.get(it.next());
      currChange.tearDownEffect(effectElement);
    }
    propertyPairs = null;
    determinedEffects = null;
    theStart = null;
    theEnd = null;
  }

  public String toString() {
    String ret = "";
    for (Iterator<String> it = determinedEffects.keySet().iterator(); it.hasNext();) {
      String theChange = it.next();
      ChangeInterface currChange = determinedEffects.get(theChange);
      ret += currChange.toString() + "; ";
    }
    return ret;
  }
}
