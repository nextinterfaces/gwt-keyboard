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
import org.adamtacy.client.ui.effects.impl.css.StyleImplementation;

import com.google.gwt.dom.client.Element;

public class NChangeClipAction implements ChangeInterface {

  NChangeScalarAction bottom = new NChangeScalarAction();
  NChangeScalarAction left = new NChangeScalarAction();
  NChangeScalarAction right = new NChangeScalarAction();
  NChangeScalarAction top = new NChangeScalarAction();

  public NChangeClipAction() {
  }

  public NChangeClipAction(String styleStart, String styleEnd) {
    this.setStartClip(styleStart);
    this.setEndClip(styleEnd);
  }

  public String performStep(Element effectElement, String styleComponentToChange,
      double progress) {
    String currTop = new Double(Math.floor(top.getValue(progress))).intValue()
        + top.getUnitOfStyleComponent();
    String currRight = new Double(Math.floor(right.getValue(progress))).intValue()
        + top.getUnitOfStyleComponent();
    String currBottom = new Double(Math.floor(bottom.getValue(progress))).intValue()
        + top.getUnitOfStyleComponent();
    String currLeft = new Double(Math.floor(left.getValue(progress))).intValue()
        + top.getUnitOfStyleComponent();
    EffectImplementation.clip(effectElement, currTop, currRight, currBottom,
        currLeft);
    // TODO
    return "";
  }

  public void reset() {
    top.reset();
    right.reset();
    bottom.reset();
    left.reset();
  }

  public void setEndClip(String endClip) {
    String[] ltbr = StyleImplementation.parseClip(endClip);
    top.setEndValue(ltbr[0]);
    right.setEndValue(ltbr[1]);
    bottom.setEndValue(ltbr[2]);
    left.setEndValue(ltbr[3]);
  }

  public void setStartClip(String startClip) {
    String[] ltbr = StyleImplementation.parseClip(startClip);
    top.setStartValue(ltbr[0]);
    right.setStartValue(ltbr[1]);
    bottom.setStartValue(ltbr[2]);
    left.setStartValue(ltbr[3]);
  }

  public void setUp(Element effectElement, String styleComponentToChange,
      double switchFrameNumber) {
    this.performStep(effectElement, styleComponentToChange, 0.0);
  }

  public void tearDownEffect(Element effectElement) {
  }

}
