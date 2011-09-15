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

import org.adamtacy.client.ui.effects.core.NMorphColor;

import com.google.gwt.dom.client.Element;

/**
 * 
 * Creates a change color effect for an Effect Panel. Colour will "morph" from a
 * start-colour to an end-colour By default the background style attribute is
 * used, but this can be altered by using the additional constructor
 * ChangeColour(styleElementName)
 * 
 * At the end of the effect, the original (background) colour of the component is
 * restored.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class ChangeColor extends NMorphColor {
  public ChangeColor() {
    super("backgroundColor");
  }
  
  public ChangeColor(Element el){
    super(el, "backgroundColor");
  }
}