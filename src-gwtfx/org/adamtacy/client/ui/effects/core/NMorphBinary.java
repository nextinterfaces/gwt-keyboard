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
 * 
 * 
 */
public class NMorphBinary extends NEffect {
  
  /** Change the ending number */
  public void setEndValue(String newEnd) {
    binaryStyleImpl.setEndValue(newEnd);
    if (!effectElements.isEmpty()) setUpEffect();
  }

  /** Change the starting number */
  public void setStartValue(String newStart) {
    binaryStyleImpl.setStartValue(newStart);
    if (!effectElements.isEmpty()) setUpEffect();
  }
  

  protected NChangeBinaryAction binaryStyleImpl = new NChangeBinaryAction();

  protected String styleComponentToChange = "";

  double threshold = 0.5;

  public NMorphBinary(String styleName) {
    super();
    styleComponentToChange = styleName;
  }
  
  public NMorphBinary(Element el, String styleName){
    this(styleName);
    addEffectElement(el);
  }
  
  public NMorphBinary(){}
  
  public NMorphBinary(Element el){
    addEffectElement(el);
  }
  
  public NMorphBinary(Element el, String styleName, String styleStart, String styleEnd) {
    this(styleName, styleStart, styleEnd);
    addEffectElement(el);
  }


  public NMorphBinary(String styleName, String styleStart, String styleEnd) {
    this(styleName);
    if (!styleStart.equals("")) {
      setStartValue(styleStart);
    }
    setEndValue(styleEnd);
  }


  @Override
  protected void onUpdate(double progress) {
    Boolean ret;
    super.onUpdate(progress);
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      ret = binaryStyleImpl.performStep(e, styleComponentToChange, progress);
      fireEvent(new EffectSteppingEvent(progress, ret));
    }
  }

  /** Reset the effect */
  @Override
  public void reset() {
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      binaryStyleImpl.performStep(e, styleComponentToChange, 0);
    }
    binaryStyleImpl.reset();
  }

  @Override
  public void setUpEffect() {
    registerEffectElement();
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      binaryStyleImpl.setUp(e, styleComponentToChange, threshold);
    }
    reset();
  }

  @Override
  public void tearDownEffect() {
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      binaryStyleImpl.tearDownEffect(e);
    } 
  }
}