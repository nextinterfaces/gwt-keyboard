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
package org.adamtacy.client.ui.effects.impl;

import org.adamtacy.client.ui.effects.core.NMorphStyle;
import org.adamtacy.client.ui.effects.impl.css.Rule;

import com.google.gwt.dom.client.Element;

public class Move extends NMorphStyle {

 
  public int getXValue() {
    return moveX;
  }

  public int getYValue() {
    return moveY;
  }

  public void setXValue(int newX) {
    moveX = newX;
      if (effectElement != null)
        setNewStyles(new Rule("s{left:0px; top: 0px;}"), new Rule("s{left: "+moveX+"px; top: "+moveY+"px;}"));
  }

  public void setYValue(int newY) {
    moveY = newY;
    if (effectElement != null)
      setNewStyles(new Rule("s{left:0px; top: 0px;}"), new Rule("s{left: "+moveX+"px; top: "+moveY+"px;}"));
  }
  
  public void setXYValue(int x, int y, int eX, int eY) {
    moveX = eX;
    moveY = eY;
    if (effectElement != null)
      setNewStyles(new Rule("s{position: absolute; left:"+x+"px; top: "+y+"px;}"), new Rule("s{left: "+moveX+"px; top: "+moveY+"px;}"));
  }

  int moveX, moveY;
  
  public Move(int x, int y) {
    super(new Rule("startMove{left:"+(0)+"px; top:"+(0)+"px;}"), new Rule("endMove{left: " + (x) + "px; top: " + (y) + "px;}"));
    moveX = x;
    moveY = y;
  }
  
  public Move(Element el, int x, int y){
    this(x,y);
    addEffectElement(el);
  }

  public Move(Rule startEntry, Rule endEntry) {
    super(startEntry, endEntry);
  }
  
  public Move(Element el, Rule startEntry, Rule endEntry){
    this(startEntry,endEntry);
    addEffectElement(el);
  }


  @Override
  public void tearDownEffect() {
    if(thePanel!=null){
      effectElement.getStyle().setProperty("top", thePanel.getElement().getStyle().getProperty("top"));
      effectElement.getStyle().setProperty("left", thePanel.getElement().getStyle().getProperty("left"));
    }
  }
}
