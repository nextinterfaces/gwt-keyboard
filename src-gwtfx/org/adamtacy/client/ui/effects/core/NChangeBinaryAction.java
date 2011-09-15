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

import org.adamtacy.client.ui.effects.impl.browsers.EffectImplementation;

import com.google.gwt.dom.client.Element;

public class NChangeBinaryAction implements ChangeInterface {

  boolean changed = true;

  /** The end number */
  String end = "Arial";

  /** The start number */
  String start = "Times";

  String styleComponentToChange = "";
  double switchFrame;
  
  public String toString(){
    return styleComponentToChange + ": " + start + "->" + end;
  }

  public NChangeBinaryAction() {
  }

  public NChangeBinaryAction(String styleStart, String styleEnd) {
    this.setStartValue(styleStart);
    this.setEndValue(styleEnd);
  }

  public Boolean performStep(Element effectElement, String styleComponentToChange,
      double progress) {
    
    if ((progress > switchFrame) && (!changed)) {
      EffectImplementation.changeFixedStyleAttribute(effectElement,
          styleComponentToChange, end);
      changed = true;
    }
    if ((progress < switchFrame) && (changed)) {
      EffectImplementation.changeFixedStyleAttribute(effectElement,
          styleComponentToChange, start);
      changed = false;
    }
    return changed;
  }

  public void reset() {
    changed = false;
  }

  /** Change the ending number */
  public void setEndValue(String newEnd) {
    end = newEnd;
  }

  /** Change the starting number */
  public void setStartValue(String newStart) {
    start = newStart;
  }
  
  public void setSwitchFrameNumber(int number){
    switchFrame = number;
  }

  public void setUp(Element effectElement, String styleComponentToChange,
      double switchFrameNumber) {
    switchFrame = switchFrameNumber;
    this.performStep(effectElement, styleComponentToChange, 0.0);
  }

  public void tearDownEffect(Element effectElement) {
  }

}
