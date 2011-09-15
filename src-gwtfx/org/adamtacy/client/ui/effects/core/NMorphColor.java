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
 * Creates a change color effect for an Effect Panel. Colour will "morph" from a
 * start-colour to an end-colour By default the background style attribute is
 * used, but this can be altered by using the additional constructor
 * ChangeColour(styleElementName)
 * 
 * At the end of the effect, the original background colour of the component is
 * restored.
 * 
 */
public class NMorphColor extends NEffect {
  
  /**
   * Change the start color to either a hex value or a rgb(r,g,b) value Both
   * contained within the input string
   * 
   * @param styleSheetColor
   */
  public void setEndColor(String styleSheetEndColor) {
    colourStyleImpl.setEndColor(styleSheetEndColor);
    if (!effectElements.isEmpty()) setUpEffect();
  }
  
  public String getEndColor(){
    return colourStyleImpl.getEndColour();
  }
  
  public String getStartColor(){
    return colourStyleImpl.getStartColour();
  }
  

  /**
   * Change the start color to either a hex value or a rgb(r,g,b) value Both
   * contained within the input string
   * 
   * @param styleSheetColor
   */
  public void setStartColor(String styleSheetStartColor) {
    colourStyleImpl.setStartColor(styleSheetStartColor);
    if (!effectElements.isEmpty()) setUpEffect();
  }

 
  protected NChangeColorAction colourStyleImpl = new NChangeColorAction();

  protected String styleComponentToChange = "";

  public NMorphColor() {
    
  }

  public NMorphColor(String styleName) {
    super();
    styleComponentToChange = styleName;
  }
  
  public NMorphColor(Element el, String styleName){
    this(styleName);
    addEffectElement(el);
  }
  
  public NMorphColor(Element el, String styleName, String styleStart, String styleEnd) {
    this(styleName, styleStart, styleEnd);
    addEffectElement(el);
  }


  public NMorphColor(String styleName, String styleStart, String styleEnd) {
    this(styleName);
    if (!styleStart.equals("")) {
      setStartColor(styleStart);
    }
    setEndColor(styleEnd);
  }


  public NMorphColor(Element el) {
    addEffectElement(el);
  }

  @Override
  protected void onUpdate(double progress) {
    String ret;
    
    super.onUpdate(progress);
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      ret = colourStyleImpl.performStep(e, styleComponentToChange, progress);
      fireEvent(new EffectSteppingEvent(progress, ret));
    }
  }


  @Override
  public void setUpEffect() {
    registerEffectElement();
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      colourStyleImpl.setUp(e, styleComponentToChange, 0);
    }
  }

  @Override
  public void tearDownEffect() {
    for (Iterator<Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = iter.next();
      colourStyleImpl.tearDownEffect(e);
    } 
  }
}