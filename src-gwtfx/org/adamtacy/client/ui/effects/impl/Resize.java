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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.adamtacy.client.ui.effects.NEffect;
import org.adamtacy.client.ui.effects.RequiresDimensions;
import org.adamtacy.client.ui.effects.core.ChangeInterface;
import org.adamtacy.client.ui.effects.core.NChangeScalarAction;
import org.adamtacy.client.ui.effects.impl.css.StyleImplementation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

public class Resize extends NEffect implements RequiresDimensions {

  static Vector<String> triggerDOM = new Vector<String>();

  static public void addToTriggerDOM(String newName){
	  triggerDOM.add(newName);
  }

  static public void addToTriggerDOM(Vector<String> newNames) {
    triggerDOM.addAll(newNames);
  }

  static public void setTriggerDOM(Vector<String> names) {
    triggerDOM = names;
  }

  HashMap<Element, Vector<ChangeInterface>> determinedEffects;

  Element effectElement;

  int endX = 0;

  double endXRatio;

  int endY = 0;

  double endYRatio;

  Vector<String> fontSizes = new Vector<String>();

  double fontSizeScalingFactor = 1.2;

  HorizontalAlignmentConstant horizAlign = HasHorizontalAlignment.ALIGN_LEFT;

  int standardFontSize = 14;
  String standardFontUnit = "px";
  int standardIndex = 4;
  int startX = 100;

  double startXRatio;

  int startY = 100;

  double startYRatio;

  VerticalAlignmentConstant vertAlign = HasVerticalAlignment.ALIGN_TOP;

  /**
   * Initialise effect and set up the basic set of DOM elements that resizing
   * will be applied to If more elements are needed to be included, use the
   * addToTriggerDOM(Vector<String> arg) method, or you can replace completely
   * using the setTriggerDOM(Vector<String> arg) method.
   */
  public Resize() {
    super();
    triggerDOM.add("IMG");
    triggerDOM.add("TABLE");
    triggerDOM.add("DIV");
  }

  public Resize(Element el) {
    this();
    addEffectElement(el);
  }

  public Resize(Element el, int start, int end) {
    this(start, end);
    addEffectElement(el);
  }

  public Resize(int start, int end) {
    this();
    startX = start;
    startY = start;
    endX = end;
    endY = end;
  }

  private void createInternalEffectsPerElement(Element currEl) {
    
    NodeList<Node> nodes = currEl.getChildNodes();
    if (nodes != null) {
      for (int i = 0; i < nodes.getLength(); i++) {
        Node n = nodes.getItem(i);
        if (n.getNodeType() == Node.ELEMENT_NODE) {
          createInternalEffectsPerElement((Element) nodes.getItem(i));
        }
      }
    }

    Vector<ChangeInterface> temp = new Vector<ChangeInterface>();

    String name = currEl.getNodeName();
    if (triggerDOM.contains(name)) {
      GWT.log("Resize Effect is Processing: " + name, null);
      
      int startVal; int endVal;
      // Deal with Widths
      startVal = getStartWidth(currEl);
      endVal = getEndWidth(currEl);
      if (startVal!=endVal){
    	  NChangeScalarAction theWidthChange = new NChangeScalarAction(startVal + "px", endVal + "px");
          temp.add(theWidthChange);
          theWidthChange.setUp(currEl, "width", 0);
      }
          
      
      // Deal with Heights
      startVal = getStartHeight(currEl);
      endVal = getEndHeight(currEl);
      if (startVal!=endVal){
    	  NChangeScalarAction theHeightChange = new NChangeScalarAction(startVal + "px", endVal + "px");
          temp.add(theHeightChange);
          theHeightChange.setUp(currEl, "height", 0);
      }

            
      // Deal with Fonts
      String res = StyleImplementation.getComputedStyle(currEl, "fontSize");

      if (res != null) {
        // We have a font to play with
        String unit = StyleImplementation.getUnits(res);
        double value = -1;
        if (unit==null || unit.equals("")) {
          // Assume we're dealing with a named font-size, as there are no units
          // so, find where this name is in our vector of font size names
          int scale = fontSizes.indexOf(res);
          // If we found it, calculate the font size
          if (scale>-1) {
            value = (double) Math.round(Math.pow(fontSizeScalingFactor, (scale - standardIndex)) * standardFontSize);
            unit = standardFontUnit;
          } 
        } else {
          // Not a named font-size, so get the value
          value = (new Double(StyleImplementation.getValue(res, unit))).doubleValue();
        }
        if (value>-1){
          NChangeScalarAction theFontChange = new NChangeScalarAction(
              (int) (value * startXRatio) + unit, (int) (value * endXRatio)
                  + unit);
          theFontChange.setUp(currEl, "fontSize", 0);
          temp.add(theFontChange);          
        } else {
            GWT.log("Don't know what to do with this font-size definition: "+res, null);
        }
      }
      
      // Do we need to manage bounding box aspects, e.g. margin etc?
      if (managesMargin) temp.addAll(getBoxChanges("margin", currEl));
      if (managesBorder) temp.addAll(getBoxChanges("border", currEl));
      if (managesPadding) temp.addAll(getBoxChanges("padding", currEl));

      
      // Put all ChangeInterfaces together into the determinedEffects object for future management
      determinedEffects.put(currEl, temp);
      
    } else {
      GWT.log("Resize Effect is Not Processing: " + name, null);
    }
    temp = null;
  }
  
  protected Vector<NChangeScalarAction> getBoxChanges(String boxAspect, Element currEl){
	  Vector<NChangeScalarAction> temp2 = new Vector<NChangeScalarAction>();
	  String end = "";
	  
	  if (boxAspect.equals("border")) end = "Width";
	  
	  int startVal;
	  int endVal;
	  
	  startVal = getStartBoxHoriz(boxAspect+"Left"+end, currEl);
	  endVal = getEndBoxHoriz(boxAspect+"Left"+end, currEl);
	  if (startVal!=endVal){
		  NChangeScalarAction marginLeft = new NChangeScalarAction(startVal + "px", endVal + "px");
		  marginLeft.setUp(currEl, boxAspect+"Left"+end, 0);
		  temp2.add(marginLeft);
	  }
	  
	  startVal = getStartBoxVert(boxAspect+"Bottom"+end, currEl);
	  endVal = getEndBoxVert(boxAspect+"Bottom"+end, currEl);
	  if (startVal!=endVal){
		  NChangeScalarAction marginBottom = new NChangeScalarAction(startVal + "px", endVal + "px");
		  marginBottom.setUp(currEl, boxAspect+"Bottom"+end, 0);
		  temp2.add(marginBottom);
	  }
	  
	  startVal = getStartBoxHoriz(boxAspect+"Right"+end, currEl);
	  endVal = getEndBoxHoriz(boxAspect+"Right"+end, currEl);
	  if (startVal!=endVal){
		  NChangeScalarAction marginRight = new NChangeScalarAction(startVal + "px", endVal + "px");
		  marginRight.setUp(currEl, boxAspect+"Right"+end, 0);
		  temp2.add(marginRight);
	  }
	  
	  startVal = getStartBoxVert(boxAspect+"Top"+end, currEl);
	  endVal = getEndBoxVert(boxAspect+"Top"+end, currEl);
	  if (startVal!=endVal){
		  NChangeScalarAction marginTop = new NChangeScalarAction(startVal + "px", endVal + "px");
		  marginTop.setUp(currEl, boxAspect+"Top"+end, 0);
		  temp2.add(marginTop);
	  }
	  return temp2;
  }
  
  // Do not manage margin, padding etc by default.
	boolean managesMargin = false;

	public boolean isManagesMargin() {
		return managesMargin;
	}

	public void setManagesMargin(boolean managesMargin) {
		this.managesMargin = managesMargin;
	}

	public boolean isManagesBorder() {
		return managesBorder;
	}

	public void setManagesBorder(boolean managesBorder) {
		this.managesBorder = managesBorder;
	}

	public boolean isManagesPadding() {
		return managesPadding;
	}

	public void setManagesPadding(boolean managesPadding) {
		this.managesPadding = managesPadding;
	}

	boolean managesBorder = false;
	boolean managesPadding = false;

	protected int getBoxSizeVert(){
		return 0;
	}

	protected int getBoxSizeHoriz(){
		return 0;
	}
	
  protected int getEndHeight(Element theWidget) {
    //int val = (int) ((theWidget.getOffsetHeight() - (2*getBoxSizeVert())) * endYRatio);
	  int val = (int) (Double.parseDouble(StyleImplementation.getValue(StyleImplementation.getComputedStyle(theWidget, "height")))* endYRatio);
	  return val;
  }
  
  protected int getEndBoxHoriz(String css, Element theWidget) {
	 String val = StyleImplementation.getComputedStyle(theWidget, css);
	 if (!val.equals("auto")&&!val.equals("undefined")){
		 double dVal = Double.parseDouble(StyleImplementation.getValue(val));
	      return (int) (dVal * endXRatio);
	 } else {
		 GWT.log("Unable to calculate style "+css+" for widget",null);
		 return -1;
	 }
  }

  protected int getStartBoxHoriz(String css, Element theWidget) {
		 String val = StyleImplementation.getComputedStyle(theWidget, css);
		 if (!val.equals("auto")&&!val.equals("undefined")){
			 double dVal = Double.parseDouble(StyleImplementation.getValue(val));
		      return (int) (dVal * startXRatio);
		 } else {
			 GWT.log("Unable to calculate style "+css+" for widget",null);
			 return -1;
		 }
	  }
  
  protected int getEndBoxVert(String css, Element theWidget) {
		 String val = StyleImplementation.getComputedStyle(theWidget, css);
		 if (!val.equals("auto")&&!val.equals("undefined")){
			 double dVal = Double.parseDouble(StyleImplementation.getValue(val));
		      return (int) (dVal * endYRatio);
		 } else {
			 GWT.log("Unable to calculate style "+css+" for widget",null);
			 return -1;
		 }
	  }

	  protected int getStartBoxVert(String css, Element theWidget) {
			 String val = StyleImplementation.getComputedStyle(theWidget, css);
			 if (!val.equals("auto")&&!val.equals("undefined")){
				 double dVal = Double.parseDouble(StyleImplementation.getValue(val));
			      return (int) (dVal * startYRatio);
			 } else {
				 GWT.log("Unable to calculate style "+css+" for widget",null);
				 return -1;
			 }
		  }

  
  protected int getEndLeft(Element theWidget) {
    if (horizAlign == HasHorizontalAlignment.ALIGN_RIGHT) {
      return (int) (((double) theWidget.getOffsetWidth() / 2 - theWidget.getOffsetWidth()
          * endXRatio) / 2);
    } else {
      return (int) ((theWidget.getOffsetWidth() - theWidget.getOffsetWidth()
          * endXRatio) / 4);
    }
  }

  protected int getEndTop(Element theWidget) {
    if (vertAlign == HasVerticalAlignment.ALIGN_BOTTOM) {
      return (int) ((double) theWidget.getOffsetHeight() / 2)
          - (int) (theWidget.getOffsetHeight() * endYRatio / 2);
    } else {
      return (int) ((double) theWidget.getOffsetHeight() / 4)
          - (int) (theWidget.getOffsetHeight() * endYRatio / 4);
    }
  }
  protected int getEndWidth(Element theWidget) {
    //int val = (int) ((theWidget.getOffsetWidth()  - (2*getBoxSizeHoriz())) * endYRatio);
	int val = (int) (Double.parseDouble(StyleImplementation.getValue(StyleImplementation.getComputedStyle(theWidget, "width")))* endXRatio);
    return val;
  }
  protected int getStartHeight(Element theWidget) {
    //int val = (int) ((theWidget.getOffsetHeight()  - (2*getBoxSizeVert())) * startYRatio);
	  int val = (int) (Double.parseDouble(StyleImplementation.getValue(StyleImplementation.getComputedStyle(theWidget, "height")))* startYRatio);
    return val;
  }
  protected int getStartLeft(Element theWidget) {
    if (horizAlign == HasHorizontalAlignment.ALIGN_RIGHT) {
      return (int) ((theWidget.getOffsetWidth() - theWidget.getOffsetWidth()
          * startXRatio) / 2);
    } else {
      return (int) ((theWidget.getOffsetWidth() - theWidget.getOffsetWidth()
          * startXRatio) / 4);
    }
  }

  protected int getStartTop(Element theWidget) {
    if (vertAlign == HasVerticalAlignment.ALIGN_BOTTOM) {
      return (int) ((double) theWidget.getOffsetHeight() / 2)
          - (int) (theWidget.getOffsetHeight() * startYRatio / 2);
    } else {
      return (int) ((double) theWidget.getOffsetHeight() / 4)
          - (int) (theWidget.getOffsetHeight() * startYRatio / 4);
    }
  }

  protected int getStartWidth(Element theWidget) {
    //int val = (int) ((theWidget.getOffsetWidth()  - (2*getBoxSizeHoriz())) * startYRatio);
	int val = (int) (Double.parseDouble(StyleImplementation.getValue(StyleImplementation.getComputedStyle(theWidget, "width")))* startXRatio);
    return val;

  }

  @Override
  protected void onUpdate(double progress) {
    super.onUpdate(progress);
    for (Iterator<Element> it = determinedEffects.keySet().iterator(); it.hasNext();) {
      Element theChange = it.next();
      Vector<ChangeInterface> currChange = determinedEffects.get(theChange);
      for (int loop = 0; loop < currChange.size(); loop++) {
        currChange.get(loop).performStep(theChange, "", progress);
      }
    }
  }

  /**
   * Set the ending height for the resize.
   * 
   * @param percentage Ending height in percentage.
   */
  public void setEndHeightPercentage(int percentage) {
    assert (percentage >= 0);
    endY = percentage;
  }

  /**
   * Set Ending percentage for width and height
   * 
   * @param percentage
   */
  public void setEndPercentage(int percentage) {
    setEndHeightPercentage(percentage);
    setEndWidthPercentage(percentage);
  }

  /**
   * Set the ending width for the resize.
   * 
   * @param percentage Ending width in percentage.
   */
  public void setEndWidthPercentage(int percentage) {
    assert (percentage >= 0);
    endX = percentage;
  }

  public void setHorizAlignment(HorizontalAlignmentConstant newHorizAlign) {
    horizAlign = newHorizAlign;
  }

  /**
   * Set the starting height for the resize.
   * 
   * @param percentage Starting height in percentage.
   */
  public void setStartHeightPercentage(int percentage) {
    assert (percentage >= 0);
    startY = percentage;
  }

  /**
   * Set Starting percentage for width and height;
   * 
   * @param percentage
   */
  public void setStartPercentage(int percentage) {
    setStartHeightPercentage(percentage);
    setStartWidthPercentage(percentage);
  }

  /**
   * Set the starting width for the resize.
   * 
   * @param percentage Starting width in percentage.
   */
  public void setStartWidthPercentage(int percentage) {
    assert (percentage >= 0);
    startX = percentage;
  }

  @Override
  public void setUpEffect() {
    registerEffectElement();
    if (effectElement == null)
      effectElement = (Element) effectElements.get(0);

    startXRatio = (double) startX / 100;
    startYRatio = (double) startY / 100;
    endXRatio = (double) endX / 100;
    endYRatio = (double) endY / 100;

    if (determinedEffects == null)
      determinedEffects = new HashMap<Element, Vector<ChangeInterface>>();
    else
      determinedEffects.clear();

    Vector<ChangeInterface> temp2 = null;

    // Deal with top if not default to align_top
    if (vertAlign != HasVerticalAlignment.ALIGN_TOP) {
      if (temp2 == null)
        temp2 = new Vector<ChangeInterface>();
      NChangeScalarAction theTopChange = new NChangeScalarAction(
          getStartTop((Element) effectElement) + "px",
          getEndTop((Element) effectElement) + "px");
      theTopChange.setUp(effectElement, "top", 0);
      temp2.add(theTopChange);
    }
    if (horizAlign != HasHorizontalAlignment.ALIGN_LEFT) {
      if (temp2 == null)
        temp2 = new Vector<ChangeInterface>();
      NChangeScalarAction theLeftChange = new NChangeScalarAction(
          getStartLeft((Element) effectElement) + "px",
          getEndLeft((Element) effectElement) + "px");
      theLeftChange.setUp(effectElement, "left", 0);
      temp2.add(theLeftChange);
    }
    if (temp2 != null)
      determinedEffects.put((Element) effectElement, temp2);


    createInternalEffectsPerElement((Element) effectElement);

    GWT.log("Number of Determined Effects: " + determinedEffects.size(), null);

    // Set up the font-size name list
    fontSizes.add("xx-small");
    fontSizes.add("x-small");
    fontSizes.add("small");
    fontSizes.add("medium");
    fontSizes.add("large");
    fontSizes.add("x-large");
    fontSizes.add("xx-large");
    
    this.isInitialised = true;
  }

  public void setVertAlignment(VerticalAlignmentConstant newVertAlign) {
    vertAlign = newVertAlign;
  }

  @Override
  public void tearDownEffect() {
    for (Iterator<Element> it = determinedEffects.keySet().iterator(); it.hasNext();) {
      Element theChange = it.next();
      Vector<ChangeInterface> currChange = determinedEffects.get(theChange);
      for (int loop = 0; loop < currChange.size(); loop++) {
        currChange.get(loop).performStep(theChange, "", 0);
      }
    }
    determinedEffects = null;
  }

  public String toString() {
    String toRet = "";
    for (Iterator<Element> it = determinedEffects.keySet().iterator(); it.hasNext();) {
      Element theChange = it.next();
      Vector<ChangeInterface> currChange = determinedEffects.get(theChange);
      for (int loop = 0; loop < currChange.size(); loop++) {
        toRet += currChange.get(loop).toString() + "; ";
      }
    }
    return toRet;
  }
}
