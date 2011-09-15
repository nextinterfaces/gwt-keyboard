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
import org.adamtacy.client.ui.effects.RequiresDimensions;
import org.adamtacy.client.ui.effects.events.EffectSteppingEvent;
import org.adamtacy.client.ui.effects.impl.browsers.EffectImplementation;

import com.google.gwt.user.client.Element;


public class NMorphClip extends NEffect implements RequiresDimensions {

  /** Change the ending number */
  public void setEndClip(String newEnd) {
    clipStyleImpl.setEndClip(newEnd);
    if ((thePanel!=null) && thePanel.isAttached()) reset();
  }

  /** Change the starting number */
  public void setStartClip(String newStart) {
    clipStyleImpl.setStartClip(newStart);
    if ((thePanel!=null) && thePanel.isAttached()) reset();
  }

  protected NChangeClipAction clipStyleImpl = new NChangeClipAction();

  protected int originalHeight;

  protected int originalWidth;


  public NMorphClip() {
  }
  
  public NMorphClip(Element el){
    addEffectElement(el);
  }

  /**
   * Expects two CSS entries such as rect(0,0,100,100) rect(0,0,0,0) and will
   * morph between the two.
   * 
   * Format is rect(top, right, bottom, left)
   * 
   * NOTE that we use the W3C preferred approach here of using commas to
   * separate the parameters; our implementation classes will deal with Internet
   * Explorer's alternative approach!
   * 
   * @param startClip
   * @param endClip
   */
  public NMorphClip(String startClip, String endClip) {
    setStartClip(startClip);
    setEndClip(endClip);
  }
  
  public NMorphClip(Element el, String startClip, String endClip) {
    addEffectElement(el);
    setStartClip(startClip);
    setEndClip(endClip);
  }

  @Override
  protected void onUpdate(double progress) {
    String ret;
    super.onUpdate(progress);
    for (Iterator<com.google.gwt.dom.client.Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = (Element) iter.next();
      ret = clipStyleImpl.performStep(e, "clip", progress);
      fireEvent(new EffectSteppingEvent(progress, ret));
    }
  }

  @Override
  public void setUpEffect() {
    registerEffectElement();
    Element el;
    for (Iterator<com.google.gwt.dom.client.Element> iter = effectElements.iterator(); iter.hasNext();){
      el = (Element) iter.next();
      originalHeight = el.getOffsetHeight();
      originalWidth = el.getOffsetWidth();
      el.getStyle().setProperty("position", "absolute");
      clipStyleImpl.setUp(el, "clip", 0);
    }
  }

  @Override
  public void tearDownEffect() {
    for (Iterator<com.google.gwt.dom.client.Element> iter = effectElements.iterator(); iter.hasNext();){
      Element e = (Element) iter.next();
      clipStyleImpl.tearDownEffect(e);
     
    e.getStyle().setProperty("overflow", "visible");
    e.getStyle().setProperty("position", "absolute"); // atanas' Brute Force fix for 'relative' being replaced with 'absolute'
    clipStyleImpl.tearDownEffect(e);
    EffectImplementation.clip(e, 0 + "px", 0 + "px", originalHeight
        + "px", originalWidth + "px");
    }
  }
}
