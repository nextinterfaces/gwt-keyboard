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

import org.adamtacy.client.ui.effects.core.NMorphScalar;

import com.google.gwt.dom.client.Element;

/**
 * A Fade effect - simply alters the numeric value of opacity.
 */
public class Fade extends NMorphScalar {
  
  public int getEndOpacity() {
    return (int) super.getEndValue();
  }

  public int getStartOpacity() {
    return (int) super.getStartValue();
  }

  /**
   * Set the ending opacity.
   * 
   * @param percentage Ending opacity percentage.
   */
  public void setEndOpacity(int percentage) {
    super.setEndValue("" + percentage + "%");

  }

  /**
   * Set the starting opacity.
   * 
   * @param percentage Starting opacity in percentage.
   */
  public void setStartOpacity(int percentage) {
    super.setStartValue("" + percentage + "%");
  }

  public Fade() {
    super("opacity");
    setEndValue("0");
    setStartValue("100");
  }
  
  public Fade(Element el){
    this();
    addEffectElement(el);
  }
}
