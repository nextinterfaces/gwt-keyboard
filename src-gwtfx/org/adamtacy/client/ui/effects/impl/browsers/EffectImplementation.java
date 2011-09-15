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

import org.adamtacy.client.ui.NEffectPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * Allows low level access to effect functionality, handing off to browser
 * specific deferred binding classes as necessary.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class EffectImplementation {

  static EffectImplementationStandard effectImpl = (EffectImplementationStandard) GWT.create(EffectImplementationStandard.class);

  /**
   * boolean to track if the reflected image has previously been set up.
   */
  static boolean setup = false;

  public static void changeFixedStyleAttribute(Element element,
      String styleComponentToChange, String value) {
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) element,
//        styleComponentToChange, value);
    element.getStyle().setProperty(styleComponentToChange, value);
  }

  public static void changeNumericalStyleAttribute(Element element,
      String styleComponentToChange, double current, String unitOfStyleComponent) {
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) element,
//        styleComponentToChange, current + unitOfStyleComponent);
    element.getStyle().setProperty(styleComponentToChange, current + unitOfStyleComponent);
  }

  /**
   * Clip the inner component of the Effect Panel to a specific set of
   * rectangle.
   * 
   * @param top topmost y-coordinate of the clip rectangle.
   * @param right rightmost x-coordinate of the clip rectangle.
   * @param bottom bottom-most y-coordinate of the clip rectangle.
   * @param left leftmost x-coordinate of the clip rectangle.
   * 
   * 
   */
  public static void clip(Element e, String top, String right, String bottom,
      String left) {
    effectImpl.clip(e, top, right, bottom, left);
  }

  /**
   * Get a reference to the image to be reflected.
   * 
   * @param thePanel
   * @return
   */
  public static Image getReflectedImage(NEffectPanel thePanel) {
    if (!setup)
      return (Image) thePanel.getPanelWidget();
    else{
      DockPanel theP = (DockPanel) thePanel.getWidget();
      return ((Image) theP.getWidget(0));
    }
  }

  /**
   * Move the EffectPanel to an absolute x,y coordinate position on the browser
   * screen.
   * 
   * @param left x-coordinate position.
   * @param top y-coordinate position.
   */
  public static void move(Element e, int left, int top) {
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "left", left
//        + "px");
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "top", top
//        + "px");
    e.getStyle().setPropertyPx("left", left);
    e.getStyle().setPropertyPx("top", top);
  }

  /**
   * Reflects an image
   * 
   * @param gap
   */
  public static void reflectImage(NEffectPanel thePanel, int height,
      double opacity, int gap, boolean setUp) {
    effectImpl.reflectImage(thePanel, height, opacity, gap, setUp);
    setup = true;
  }
  

  /**
   * Set the colour (color) of a DOM element
   * 
   * @param effectElement
   * @param styleComponentToChange
   * @param colour
   */
  public static void setColour(Element effectElement,
      String styleComponentToChange, String colour) {
    effectImpl.setColour(effectElement, styleComponentToChange, colour);
  }

  /**
   * Change the positioning attribute of an element to allow us to move it
   * around on the page.
   * 
   * @param effectElement
   */
  public static void setMoveable(Element effectElement, boolean flag) {
//    if (flag)
//      effectElement.getStyle().setProperty("position", "absolute"); // atanas' Brute Force fix for 'relative' being replaced with 'absolute'
//    else
//      effectElement.getStyle().setProperty("position", "absolute");
  }

  /**
   * Set the opacity of the component held by the EffectPanel
   * 
   * @param value Opacity value.
   */
  public static void setOpacity(Element e, double value) {
    effectImpl.setOpacity(e, value);
  }
  
  public static String getOpacityText(double val){
    return effectImpl.getOpacityText(val);
  }


  public static void unReflectImage(NEffectPanel thePanel) {
    if(setup){
      thePanel.setWidget(getReflectedImage(thePanel));
      setup = false;
    }
  }

  /**
   * Standard browsers can use the normal CSS transparent value - 
   * See EffectImplementationIE6 for the odd one out.......note that for IE the color pink is chosen 
   * as the standard color to make transparent; this can be changed by using the setTransparentColor() method.
   * (the above is irrelevant for standards compliant browsers).
   * @param effectElement
   * @param styleComponentToChange
   */
  public static void setTransparent(Element effectElement,
      String styleComponentToChange) {
    effectImpl.setTransparent(effectElement, styleComponentToChange);
  }
  
  /**
   * Needed for IE6 to allow programmer to select color that is made transparent; all browsers that follow standards
   * do not require this.
   * 
   * @param color
   */
  public static void setTransparentColor(String color){  
    effectImpl.setTransparentColor(color);
  }
  
  /**
   * Gets the layout definition that IE requires to use filters.
   * Only has meaning for IE.
   */
  public static String getIELayoutDefinition(){
    return effectImpl.getLayoutDefinition();
  }
  
  /**
   * Sets the layout definition that IE requires to use filters.
   * Is only required for IE.
   */
  public static void setIELayoutDefinition(String id, String val){
    effectImpl.setLayoutDefinition(id,val);
  }

}