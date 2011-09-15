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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;

public class ClipBase extends NMorphClip {

  int height;
  int width;

  public ClipBase() {
    super();
  }

  public ClipBase(String startClip, String endClip) {
    super(startClip, endClip);
  }
  
  public ClipBase(Element el) {
    super(el);
  }

  public ClipBase(Element el, String startClip, String endClip) {
    super(el, startClip, endClip);
  }
  
  /**
   * Adds a new Element to be managed by this effect.
   * See enhancement 104.
   * Note for clips we only support 1 element at the moment!
   * @param el
   */
  public void addEffectElement(Element el){
    setEffectElement(el);
  }
  
  

  public void setUpEffect(final Command command) {
    super.setUpEffect();
    DeferredCommand.addCommand(new Command() {
      public void execute() {
          height = effectElements.get(0).getOffsetHeight();
          width = effectElements.get(0).getOffsetWidth();
        command.execute();
      }
    });
  }
}
