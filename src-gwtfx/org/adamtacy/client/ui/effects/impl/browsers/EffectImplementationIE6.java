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

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

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
public class EffectImplementationIE6 extends EffectImplementationStandard {


  private class IEFixLoadListener implements LoadHandler {
    public void onLoad(LoadEvent event) {
    }
  }

  boolean loaded = false;

  IEFixLoadListener theIEFix = new IEFixLoadListener();

  /**
   * Reflects an image
   */
  @Override
  public void reflectImage(final NEffectPanel thePanel, final int height,
      final double opacity, final int gap, boolean setUp) {
    final DockPanel container = new DockPanel();
    final Image v = new Image();
    SimplePanel gapPanel;

    Widget w = thePanel.getPanelWidget();
    if (!(w instanceof Image)) {
      w = ((VerticalPanel) w).getWidget(0);
    }

    // For some reason when reloading a page in IE a complete refresh is not
    // made
    // and so this effect is not fired; by adding this load listener overcomes
    // this problem
    // go figure......
    if (!loaded) {
      ((Image) w).addLoadHandler(theIEFix);
      loaded = true;
    } else {
     // ((Image) w).removeLoadHandler(theIEFix);
    }
    
    gapPanel = new SimplePanel();
    gapPanel.setWidth("100%");
    gapPanel.add(new HTML("&nbsp;"));
    gapPanel.setHeight(gap + "px");

    int imgWidth = w.getOffsetWidth();
    int imgHeight = w.getOffsetHeight();
    int canvasHeight = new Double(imgHeight * height / 100).intValue();

    v.setUrl(((Image) w).getUrl());

    
    v.getElement().getStyle().setProperty("filter",
        "flipv progid:DXImageTransform.Microsoft.Alpha(opacity="
            + (opacity * 100)
            + ", style=1, finishOpacity=0, startx=0, starty=0, finishx=0, finishy="
            + (height * 100) + ")");

    v.setHeight(canvasHeight + "px");
    v.setWidth(imgWidth + "px");

    thePanel.remove(w);

    container.add(w, DockPanel.NORTH);
    container.add(v, DockPanel.SOUTH);
    container.add(gapPanel, DockPanel.CENTER);

    thePanel.add(container);
  }
  
  /**
   * In IE case, just set the colour if it is #rrggbb or rgb(r,g,b)
   * if it's rgba(r,g,b,a) then make it rgb(r,g,b)
   * @param effectElement
   * @param styleComponentToChange
   * @param colour
   */
  public void setColour(Element effectElement, String styleComponentToChange,
      String colour) {
    if (colour.startsWith("rgba")){
      String t3 = "rgb" + colour.substring(4,(colour.lastIndexOf(","))) + ")";
      colour = t3;
    }
    effectElement.getStyle().setProperty(styleComponentToChange, colour);  
  }
  
  String layoutIEId = "zoom";
  String layoutIEVal = "1";
  
  /**
   * Returns the layout definition that IE requires to use filters
   */
  @Override
  public String getLayoutDefinition(){
    return layoutIEId + ":" + layoutIEVal;
  }
  
  /**
   * Sets the layout definition that IE requires to use filters.
   */
  @Override
  public void setLayoutDefinition(String id, String val){
    layoutIEId = id;
    layoutIEVal = val;
  }

  /**
   * Set the opacity of the component held by the EffectPanel
   * 
   * @param value Opacity value.
   */
  @Override
  public void setOpacity(Element e, double value) {
    //Just set the layout rather than checking and then possibly setting - worse case is now 1 computation
//    if (!DOM.getStyleAttribute((com.google.gwt.user.client.Element) e, "zoom").equals(
//        "1"))
//      DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, layoutIEId, layoutIEVal);
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "filter",
//        "progid:DXImageTransform.Microsoft.Alpha(opacity=" + (int) value + ")");
    e.getStyle().setProperty(layoutIEId, layoutIEVal);
    e.getStyle().setProperty("filter", "progid:DXImageTransform.Microsoft.Alpha(opacity=" + (int) value + ")");
  }
  
  public String getOpacityText(double value){
    return "filter: alpha(opacity="+ (int) value + "); ";
  }
  
  String transparentColor = "#000001";
  
  /**
   * IE6 specific function to set a transparent color.
   * @param effectElement
   * @param styleComponentToChange
   */
  public void setTransparent(Element effectElement,
      String styleComponentToChange) {
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement, layoutIEId, layoutIEVal);
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement,
//        styleComponentToChange, transparentColor);
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement,
//        "filter", "chroma(color="+transparentColor+")");
    effectElement.getStyle().setProperty(layoutIEId, layoutIEVal);
    effectElement.getStyle().setProperty(styleComponentToChange, transparentColor);
    effectElement.getStyle().setProperty("filter", "chroma(color="+transparentColor+")");
  }
  
  public void setTransparentColor(String color){ 
    transparentColor = color;
  }

}