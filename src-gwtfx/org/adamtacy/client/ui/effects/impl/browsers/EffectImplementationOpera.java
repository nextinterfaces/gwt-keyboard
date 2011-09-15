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

import org.adamtacy.client.ui.Canvas;
import org.adamtacy.client.ui.NEffectPanel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Deferred binding class allowing access to low level effect functionality for
 * Opera. Class extends the standard implementation, overriding the reflect()
 * method to allow Opera to handle reflecting an image in its own way.
 * 
 * @author Adam Tacy
 * @version 3.0
 *  
 */
public class EffectImplementationOpera extends EffectImplementationStandard {
  
  /**
   * In Opera case, just set the colour if it is #rrggbb or rgb(r,g,b)
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
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement,
//        styleComponentToChange, colour);    
  }
  
  @Override
  protected native void reflect(Element ct, Element img, int canvasHeight,
      double opacity)/*-{
        	    var imgWidth = img.width;
        	    var imgHeight = img.height;
        	    ct.width = imgWidth;
        	    ct.height = canvasHeight;
        	    var context = ct.getContext('2d');
        		context.save();
        		context.translate(0, imgHeight-1);
        		context.scale(1, -1);
        		context.drawImage(img, 0, 0, imgWidth, imgHeight);
        		context.restore();
        		context.globalCompositeOperation = 'destination-out';
        		var gradient = context.createLinearGradient(0, 0, 0, imgHeight);
        		gradient.addColorStop(0, 'rgba(255, 255, 255, '+(1-opacity)+')');
        		var length =  (canvasHeight/imgHeight);
        		gradient.addColorStop(length, 'rgba(255, 255, 255, 1.0)');
        		gradient.addColorStop(1, 'rgba(255, 255, 255, 1.0)');
        		context.fillStyle = gradient;
        		context.fillRect(0,0,imgWidth,canvasHeight);
        		//context.fill();
        	}-*/;

  /**
   * Reflects an image
   */
  @Override
  public void reflectImage(NEffectPanel thePanel, int height, double opacity,
      int gap, boolean setUp) {

    DockPanel container;
    Widget w;
    Canvas v;
    SimplePanel gapPanel;

    if (setUp) {
      w = (Image) ((DockPanel) thePanel.getPanelWidget()).getWidget(0);
      v = (Canvas) ((DockPanel) thePanel.getPanelWidget()).getWidget(WHERE_IS_REFLECTION);
      v.setSize(1, 1);
      // v = new Canvas();
      container = (DockPanel) thePanel.getPanelWidget();
      // Remove from panel now as this may make any animations look much
      // smoother
      // If not, there is a danger on slow(er) systems that the reflection hangs
      // out from image until re-drawn.
      container.remove(WHERE_IS_REFLECTION);
    } else {
      container = new DockPanel();
      w = getReflectedImage(thePanel, setUp);
      v = new Canvas();
    }

    gapPanel = new SimplePanel();
    gapPanel.setWidth("100%");
    gapPanel.add(new HTML("&nbsp;"));
    gapPanel.setHeight(gap + "px");

    int imgHeight = w.getOffsetHeight();
    int canvasHeight = (int) ((imgHeight * height) / 100);

    reflect(v.getElement(), w.getElement(), canvasHeight, opacity);

    if (setUp) {
      container.add(v, DockPanel.SOUTH);
    } else {
      container.add(w, DockPanel.NORTH);
      container.add(v, DockPanel.SOUTH);
      thePanel.add(container);
    }
    container.add(gapPanel, DockPanel.CENTER);
    container.getElement().getStyle().setProperty("overflow", "hidden");
    //DOM.setElementAttribute(container.getElement(), "overflow", "hidden");
    container.setHeight(imgHeight + canvasHeight + "px");
  }

}