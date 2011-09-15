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
package org.adamtacy.client.ui.effects.impl.browsers;


import com.google.gwt.dom.client.Element;

/**
 * Deferred binding class allowing access to low level effect functionality for Internet Explorer.
 * 
 * Class extends the standard class, but provides IE specific implementations for the following methods:
 * 
 * <li>
 *   <ul>reflectImage (uses IE specific filters on image instead of Canvas)</ul>
 *   <ul>setOpacity (uses IE specific filters and values between 0 and 100, instead of direct CSS opacity)</ul>
 * </li>
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class EffectImplementationIE8 extends EffectImplementationIE6 {

  /**
   * Set the opacity of the component held by the EffectPanel
   * 
   * @param value Opacity value.
   */
  @Override
  public void setOpacity(Element e, double value) {
//    if (!DOM.getStyleAttribute((com.google.gwt.user.client.Element) e, "zoom").equals(
//        "1"))
//      DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "zoom", "1");
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "-ms-filter",
//        "\"progid:DXImageTransform.Microsoft.Alpha(opacity=" + (int) value + ")\"");
    if (!e.getStyle().getProperty("zoom").equals("1"))
      e.getStyle().setProperty("zoom","1");
    e.getStyle().setProperty("-ms-filter",
    "\"progid:DXImageTransform.Microsoft.Alpha(opacity=" + (int) value + ")\"");
  }
  
  public String getOpacityText(double value){
    return "-ms-filter: \"progid:DXImageTransform.Microsoft.Alpha(opacity="+ (int) value + ");\" ";
  }
    
  /**
   * IE6 specific function to set a transparent color.
   * @param effectElement
   * @param styleComponentToChange
   * 
   * TODO Update for IE8
   */
  public void setTransparent(Element effectElement,
      String styleComponentToChange) {
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement,
//        styleComponentToChange, transparentColor);
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement,
//        "filter", "progid:DXImageTransform.Microsoft.Chroma(color="+transparentColor+")");
    effectElement.getStyle().setProperty(styleComponentToChange, transparentColor);
    effectElement.getStyle().setProperty("filter", "progid:DXImageTransform.Microsoft.Chroma(color="+transparentColor+")");
  }
}