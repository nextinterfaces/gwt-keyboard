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

import org.adamtacy.client.ui.effects.core.NMorphClip;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

/**
 * 
 * Creates a wipe down effect for an Effect Panel
 * 
 */
public class CollapseVertically extends NMorphClip {

  public CollapseVertically() {
    super();
  }

  public CollapseVertically(String startClip, String endClip) {
    super(startClip, endClip);
  }
  
  public CollapseVertically(Element el) {
    super();
    addEffectElement(el);
  }

  public CollapseVertically(Element el, String startClip, String endClip) {
    super(startClip, endClip);
    addEffectElement(el);
  }
  
  Element effectElement;

  @Override
  public void setUpEffect() {
    super.setUpEffect();
    DeferredCommand.addCommand(new Command() {
      public void execute() {
        Element el = effectElements.get(0);       
        int height = el.getOffsetHeight();
        int width = el.getOffsetWidth();
        int halfHeight = new Double(Math.ceil(height / 2)).intValue() + 1;
        setStartClip(
            "rect(0px," + width + "px," + height + "px,0px)");
        setEndClip(
            "rect(" + halfHeight + "px," + width + "px," + halfHeight
                + "px,0px)");
      }
    });
  }
}