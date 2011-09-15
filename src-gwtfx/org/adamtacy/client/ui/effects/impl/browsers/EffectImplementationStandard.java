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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Deferred Binding class allowing access to low level effect functionality for standard browsers.
 * 
 * @author Adam Tacy
 * @version 3.0
 *  
 */
public class EffectImplementationStandard {



  final protected int WHERE_IS_REFLECTION = 2;

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
  public void clip(Element e, String top, String right, String bottom,
      String left) {
    StringBuffer clipString = new StringBuffer("rect");
    clipString.append("(");
    clipString.append(top);
    clipString.append(" ");
    clipString.append(right);
    clipString.append(" ");
    clipString.append(bottom);
    clipString.append(" ");
    clipString.append(left);
    clipString.append(")");
    e.getStyle().setProperty("clip", clipString.toString());
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "clip",
//        clipString.toString());
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "clip",
//        "rect(" + top + " " + right + " " + bottom + " " + left + ")");
  }

  /**
   * Get a reference to the image to be reflected.
   * 
   * @param thePanel
   * @return
   */
  public Image getReflectedImage(NEffectPanel thePanel, boolean setUp) {
    if (!setUp)
      return (Image) thePanel.getPanelWidget();
    else
      return (Image) (((DockPanel) thePanel.getPanelWidget()).getWidget(0));
  }

  /**
   * The code to reflect an element. This comment block gives a verbatim
   * description as we remove that from the JSNI code to keep output as short as
   * possible (GWT does not, as far as I am aware, remove comments from JSNI
   * code... // Get the canvas context var context = ct.getContext('2d'); //
   * save current state (i.e. identity transformation) context.save(); // move
   * whatever will be drawn to the bottom of the canvas context.translate(0,
   * imgHeight-1); // flip it in y-axis context.scale(1, -1); // draw the image
   * context.drawImage(img, 0, 0, imgWidth, imgHeight); // Get back to the
   * identity transformation context.restore(); // Now we're going to draw on
   * top... context.globalCompositeOperation = 'destination-out'; // Create a
   * gradient var gradient = context.createLinearGradient(0, 0, 0, imgHeight);
   * gradient.addColorStop(0, 'rgba(255, 255, 255, '+(1-opacity)+')'); var
   * length = (canvasHeight/imgHeight); gradient.addColorStop(length, 'rgba(255,
   * 255, 255, 1.0)'); gradient.addColorStop(1, 'rgba(255, 255, 255, 1.0)'); //
   * Set the fill stylr context.fillStyle = gradient; // And draw....
   * context.fillRect(0,0,imgWidth,imgHeight);
   * 
   */

  protected native void reflect(Element ct, Element img, int cH, double opacity)/*-{

        		var imgWidth = img.width;
        	    var imgHeight = img.height;

        	    var c = ct.getContext('2d');

        	    ct.width = imgWidth;
        	    ct.height = cH;
        	    ct.style.width = imgWidth + 'px';
        	    ct.style.height = cH + 'px';

        		c.save();
        		c.translate(0, img.height-1);
        		c.scale(1, -1);
        		c.drawImage(img, 0, 0, img.width, img.height);
        		c.restore();
        		c.globalCompositeOperation = 'destination-out';
        		var g = c.createLinearGradient(0, 0, 0, cH);
        		g.addColorStop(0, 'rgba(255, 255, 255, '+(1-opacity)+')');
        		g.addColorStop(1, 'rgba(255, 255, 255, 1.0)');
        		c.fillStyle = g;
        		c.fillRect(0,0,img.width,cH);
        	}-*/;

  /**
   * Reflects an image by creating a DockPanel where the original image is place
   * in the top and a canvas object containing a refection of the original image
   * is placed below. At the moment I can't seem to reuse the original canvas so
   * if any action is performed requiring the reflection to be re-drawn a new
   * canvas object is required (compare that to the Opera version where reuse is
   * possible)
   * 
   * @param gap
   */
  public void reflectImage(NEffectPanel thePanel, int height, double opacity,
      int gap, boolean setUp) {

    DockPanel container;
    Widget w;
    SimplePanel gapPanel;
    Canvas v;

    w = getReflectedImage(thePanel, setUp);
    if (setUp) {
      v = new Canvas();
      container = (DockPanel) thePanel.getPanelWidget();
      // Remove from panel now as this may make any animations look much
      // smoother
      // If not, there is a danger on slow(er) systems that the reflection hangs
      // out from image until re-drawn.
      container.remove(WHERE_IS_REFLECTION);
    } else {
      container = new DockPanel();
      v = new Canvas();
    }

    gapPanel = new SimplePanel();
    gapPanel.setWidth("100%");
    gapPanel.add(new HTML("&nbsp;"));
    gapPanel.setHeight(gap + "px");

    int imgWidth = w.getOffsetWidth();
    int imgHeight = w.getOffsetHeight();
    int canvasHeight = (int) ((imgHeight * height) / 100);
    v.setSize(imgWidth, canvasHeight);

    reflect(v.getElement(), w.getElement(), canvasHeight, opacity);
    if (setUp) {
      container.add(v, DockPanel.SOUTH);
    } else {
      container.add(w, DockPanel.NORTH);
      container.add(v, DockPanel.SOUTH);
      thePanel.add(container);
    }
    container.add(gapPanel, DockPanel.CENTER);
  }

  /**
   * Set the opacity of the component held by the EffectPanel
   * 
   * @param value Opacity value.
   */
  public void setOpacity(Element e, double value) {
    e.getStyle().setProperty("opacity", ""+value/100);
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) e, "opacity", ""
//        + value / 100);
  }
  
  public String getOpacityText(double value){
    return "opacity" + value / 100 + "; ";
  }

  /**
   * For normal browsers, nothing more to do...
   * @param effectElement
   * @param styleComponentToChange
   */
  public void setTransparent(Element effectElement,
      String styleComponentToChange) {
    effectElement.getStyle().setProperty(styleComponentToChange, "transparent");
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement,
//        styleComponentToChange, "transparent");
  }
  
  /**
   * For normal browsers, nothing more to do...
   * @param color
   */
  public void setTransparentColor(String color){  
    return;
  }

  /**
   * In standard case, just set the colour
   * @param effectElement
   * @param styleComponentToChange
   * @param colour
   */
  public void setColour(Element effectElement, String styleComponentToChange,
      String colour) {
    effectElement.getStyle().setProperty(styleComponentToChange, colour);
//    DOM.setStyleAttribute((com.google.gwt.user.client.Element) effectElement,
//        styleComponentToChange, colour);    
  }

  /**
   * Gets the layout definition that IE requires to use filters.
   * returns empty string for any browser other than IE as it has no use.
   */
  public String getLayoutDefinition() {
    GWT.log("getLayoutDefinition() is only available for IE (it is not needed for other browsers)", null);
    return "";
  }

  /**
   * Sets the layout definition that IE requires to use filters.
   * Is a null operation except for within IE
   */
  public void setLayoutDefinition(String id, String val) {
    GWT.log("setLayoutDefinition() is only available for IE (it is not needed for other browsers)", null);    
  }
}