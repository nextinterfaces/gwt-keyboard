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

import java.util.Iterator;

import org.adamtacy.client.ui.effects.NEffect;
import org.adamtacy.client.ui.effects.events.EffectSteppingEvent;

import com.google.gwt.dom.client.Element;

/**
 * Class to morph between 2 scalar values, e.g. 0 -> 100
 * 
 */
public class NMorphScalar extends NEffect {
  
  /**
   * Instance of NChangeScalar Action
   */
  protected NChangeScalarAction scalarStyleImpl = new NChangeScalarAction();

  // Which style component are we changing
  protected String styleComponentToChange = "";

  public NMorphScalar(){}
    
  public NMorphScalar(Element el, String styleName){
    this(styleName);
    addEffectElement(el);
  }


  public NMorphScalar(Element el, String styleName, String styleStart, String styleEnd){
    this(styleName, styleStart, styleEnd);
    addEffectElement(el);
  }
  

  public NMorphScalar(String styleName) {
    super();
    styleComponentToChange = styleName;
  }

  public NMorphScalar(String styleName, String styleStart, String styleEnd) {
    this(styleName);
    if (!styleStart.equals("")) {
      setStartValue(styleStart);
    }
    setEndValue(styleEnd);
  }

  public double getEndValue() {
    return scalarStyleImpl.end;
  }

  public double getStartValue() {
    return scalarStyleImpl.start;
  }
  
  public StepFunctionInterface getStepFunction(){
    return scalarStyleImpl.getStepFunction();
  }
  
  @Override
  protected void onUpdate(double progress) {
    super.onUpdate(progress);
      for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
        Element e = iter.next();
        Double ret = scalarStyleImpl.performStep(e, styleComponentToChange, progress);
        fireEvent(new EffectSteppingEvent(progress, ret));
      }
  }
  
  /** Change the ending number */
  public void setEndValue(String newEnd) {
    scalarStyleImpl.setEndValue(newEnd);
    if (!effectElements.isEmpty()) setUpEffect();
  }
  

  /** Change the starting number */
  public void setStartValue(String newStart) {
    scalarStyleImpl.setStartValue(newStart);
    if (!effectElements.isEmpty()) setUpEffect();
  }
  
  public void setStepFunction(StepFunctionInterface newStepFunction){
    scalarStyleImpl.setStepFunction(newStepFunction);
  }
  

  @Override
  public void setUpEffect() {
    registerEffectElement();
      for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
        Element e = iter.next();
        scalarStyleImpl.setUp(e, styleComponentToChange, 0);
      }
  }

  @Override
  public void tearDownEffect() {
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      scalarStyleImpl.tearDownEffect(e);
    } 
  }
}