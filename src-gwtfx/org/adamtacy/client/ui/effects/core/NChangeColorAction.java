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
package org.adamtacy.client.ui.effects.core;

import org.adamtacy.client.ui.effects.exception.ColorException;
import org.adamtacy.client.ui.effects.impl.browsers.EffectImplementation;
import org.adamtacy.client.ui.effects.impl.css.StyleImplementation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;

public class NChangeColorAction implements ChangeInterface {

  double currAlpha;

  int[] start = new int[4];
  int[] end = new int[4];
  int[] curr = new int[3];


  public String getStartColour(){
    return "rgb("+start[0]+","+start[1]+","+start[2]+")";
  }
  
  public String getEndColour(){
    return "rgb("+end[0]+","+end[1]+","+end[2]+")";
  }
  
  public NChangeColorAction() {
  }
    
  public String toString(){
    return styleComponentToChange + ": " + start + "->" + end;
  }

  public NChangeColorAction(String styleStart, String styleEnd) {
    this();
    this.setStartColor(styleStart);
    this.setEndColor(styleEnd);
  }


  public String performStep(Element effectElement, String styleComponentToChange,
      double progress) {
    double factor2 = progress;
    double factor1 = 1 - factor2;
    String col;
    
    // Issue 43 to include handling transparent.
    // Note should use background-color as transparent too in the style
    if (isTransparent){
      EffectImplementation.setTransparent(effectElement, styleComponentToChange);
      col = "transparent";
    } else {

    // Calculate new colour
    curr[0] = (int) Math.floor(start[0] * factor1 + end[0] * factor2);
    curr[1] = (int) Math.floor(start[1] * factor1 + end[1] * factor2);
    curr[2] = (int) Math.floor(start[2] * factor1 + end[2] * factor2);

    StringBuffer colour = new StringBuffer();

    
    colour.append("(");
    colour.append(curr[0]);
    colour.append(",");
    colour.append(curr[1]);
    colour.append(",");
    colour.append(curr[2]);
    if ((start[3]<100)||(end[3]<100)){
      currAlpha = (end[3] - start[3]) * factor2;
      if (currAlpha < 0) currAlpha = -currAlpha;
      colour.append(",");
      colour.append((currAlpha/100));
      colour.insert(0,"rgba");
    } else colour.insert(0,"rgb");
    
    colour.append(")");
    col = colour.toString();
    EffectImplementation.setColour(effectElement, styleComponentToChange, col);
    }
    return col;
  }

  public void reset() {
  }
  
  boolean isTransparent = false;



  /**
   * Change the start color to either a hex value or a rgb(r,g,b) value Both
   * contained within the input string
   * 
   * @param styleSheetColor
   */
  public void setEndColor(String styleSheetEndColor) {
    if (isTransparent) return;
    if (StyleImplementation.isTransparent(styleSheetEndColor)){
      isTransparent = true;
      return;
    }
    try{
      holderRGB = StyleImplementation.parseColour(styleSheetEndColor);
    } catch (ColorException e){
        GWT.log("Parse Colour Exception",e);
    }finally {
      end = holderRGB;
      if ((holderRGB[3]>-1)){
        end[3] = holderRGB[3];
      } else {
        end[3] = 100;
      }
    }
  }
  
  int[] holderRGB = new int[3];


  /**
   * Change the start color to either a hex value or a rgb(r,g,b) value Both
   * contained within the input string
   * 
   * @param styleSheetColor
   */
  public void setStartColor(String styleSheetStartColor) {
    if (isTransparent) return;
    if (StyleImplementation.isTransparent(styleSheetStartColor)){
      isTransparent = true;
      return;
    }
    try{
      holderRGB = StyleImplementation.parseColour(styleSheetStartColor);
    } catch (ColorException e){
      GWT.log("Couldn't parse colour: "+styleSheetStartColor,e);
    } finally {
      start = holderRGB;
      // Align alpha value of end if we have one provided for start but not for end
      if ((holderRGB[3]>-1)){
        start[3] = holderRGB[3];
      } else {
        start[3] = 100;
      }
    }
  }
 
  
  String styleComponentToChange;
  String startColour;

  public void setUp(Element effectElement, String styleComponentToChange,
      double switchFrameNumber) {
    this.styleComponentToChange = styleComponentToChange;
    if (start[0] < 0) {
      // Haven't had the colour set for a start...so let's try and inherit it
      startColour = StyleImplementation.getComputedStyle(effectElement,
          styleComponentToChange);
      this.setStartColor(startColour);
    }
    this.performStep(effectElement, styleComponentToChange, 0.0);
  }

  public void tearDownEffect(Element effectElement) {
    EffectImplementation.setColour(effectElement, styleComponentToChange, startColour);
  }
}
