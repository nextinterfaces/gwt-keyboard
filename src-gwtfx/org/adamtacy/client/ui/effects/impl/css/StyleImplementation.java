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
package org.adamtacy.client.ui.effects.impl.css;

import java.util.HashMap;

import org.adamtacy.client.ui.effects.exception.ColorException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;

/**
 * Helper class providing access to Style manipulation functionality.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class StyleImplementation {

  static StyleSheetImplementationStandard styleSheetImpl = (StyleSheetImplementationStandard) GWT.create(StyleSheetImplementationStandard.class);

  /**
   * JavaScript require the use of Camel style for CSS properties i.e. font-size
   * becomes fontSize. This function takes a non-camelized text String and
   * returns the Camelized version.
   * 
   * Takes aaa-bbb-ccc and returns aaaBbbCcc
   * 
   * @param input
   * @return
   */
  public static String camelize(String input) {
    StringBuffer output = new StringBuffer();

    String check = camelized.get(input);
    if (check == null) {
      String[] temp = input.split("-");
      output.append(temp[0].toLowerCase());
      for (int loop = 1; loop < temp.length; loop++) {
        output.append(temp[loop].substring(0, 1).toUpperCase());
        output.append(temp[loop].substring(1, temp[loop].length()).toLowerCase());
      }
      camelized.put(input, output.toString());
      unCamelized.put(output.toString(), input);
      return output.toString();
    } else {
      return check;
    }
  }

  /**
   * 
   * Takes aaaBbbCcc and returns aaaBbbCcc aaa-bbb-ccc

   * 
   * @param input
   * @return
   */
  public static String unCamelize(String input) {
    String output = "";
    output = unCamelized.get(input);
    if (output == null) {
      StringBuffer out = new StringBuffer();
      for (int loop=0; loop < input.length(); loop++){
        char theChar = input.charAt(loop);
        if(Character.isUpperCase(theChar)){
          out.append("-");
        }
        out.append((""+theChar).toLowerCase());
      }
      output = out.toString();
      unCamelized.put(input, output);
      camelized.put(output, input);
    }
    return output;
  }

  /**
   * Simple HashMap to hold cached camelized strings.
   */
  static HashMap<String, String> camelized = new HashMap<String, String>();
  static HashMap<String, String> unCamelized = new HashMap<String, String>();

  /**
   * Get the computed style for a particular CSS property. Currently gwt-fx
   * cannot cope if the computed style for any property turns out to be "auto"
   * so we throw an exception suggesting that the start value for the CSS
   * property needs to be defined. This method wraps around the JSNI method
   * getComputedStyleJSNI().
   * 
   * Added code to deal with "auto" if style is width or height to address IE issue identified in Issue 130.
   * Switched String equality checking as advised in comments by rkrysinski in Issue 130.
   * 
   * @param element DOM element to query
   * @param styleProperty
   * @return
   */
  public static String getComputedStyle(Element element, String styleProperty) {
    String sP = styleSheetImpl.correctStyleProperty(styleProperty);
    String result = styleSheetImpl.getComputedStyleJSNI(element, sP);
    if ("auto".equals(result)){
    	if(styleProperty=="width")result = getWidth(element);
    	else if (styleProperty=="height")result = getHeight(element);
    	else throw new RuntimeException("Computed style for " + styleProperty
          + " on Element "+element+" is auto - try setting a start value explicitly");
    	if (result.startsWith("undefined")) throw new RuntimeException("Computed style for " + styleProperty
    	          + " on Element "+element+" is undefined - try setting a start value explicitly");
    }
    else if ("transparent".equals(result)||removeSpaces(result).equals("rgba(0,0,0,0)"))
    	return getComputedStyle(element.getParentElement(), styleProperty);
    return result;
  }

  /**
   * Added as part of Issue 130 to address IE returning "auto"
   * @param element
   * @return
   */
  private static native String getWidth(Element element)/*-{return element.offsetWidth+'px';}-*/;
  
  /**
   * Added as part of Issue 130 to address IE returning "auto"
   * @param element
   * @return
   */
  private static native String getHeight(Element element)/*-{return element.offsetHeight+'px';}-*/;
  
  /**
   * Remove spaces from a String
   * 
   * Null checking pre-condition added due to comment from rkrysinski in Issue 130.
   * 
   * @param s
   * @return
   */
  public static String removeSpaces(String s) {
	  String t="";
	  if (s!=null){
		  String[] st = s.split(" ");
	  	  	for (int i =0;i < st.length; i++) t += st[i];
	  }
	  return t;
	}

  

  /**
   * From a particular style property value return the unit type - which can be
   * one of the following: pt, px, em, % or nothing.
   * 
   * @param stylePropertyValue
   * @return
   */
  public static String getUnits(String stylePropertyValue) {
    if (stylePropertyValue.endsWith("pt"))
      return "pt";
    if (stylePropertyValue.endsWith("px"))
      return "px";
    if (stylePropertyValue.endsWith("em"))
      return "em";
    if (stylePropertyValue.endsWith("%"))
      return "%";
    return "";
  }

  /**
   * Given a style property value with a potential unit (em, pt, px, %) suffix,
   * return the pure numeric value.
   * 
   * @param stylePropertyValue input value.
   * @param unitOfStyleComponent numeric equivalent of input value
   * @return
   */
  public static String getValue(String stylePropertyValue,
      String unitOfStyleComponent) {
    if (unitOfStyleComponent.equals(""))
      return stylePropertyValue;
    return stylePropertyValue.split(unitOfStyleComponent)[0];
  }
  
  /**
   * Courtesy method to get value quicker
   * @param stylePropertyValue
   * @return
   */
  public static String getValue(String stylePropertyValue){
    return getValue(stylePropertyValue, getUnits(stylePropertyValue));
  }

  public static boolean isClip(String style) {
    return style.contains("clip");
  }

  public static boolean isColour(String style) {
    return ((style.contains("rgb(")) || (style.contains("rgba("))
        || (style.contains("#")) || (style.contains("transparent")));
  }

  public static boolean isScalar(String style, String value) {
    return ((value.contains("pt")) || (value.contains("px"))
        || (value.contains("em")) || (value.contains("%")) || (style.contains("opacity")));
  }

  /**
   * Expect something of the form rect(a,b,c,d)
   * 
   * @param clip value of form rect(a,b,c,d)
   * @return String array representing the top, right, bottom, left values in
   *         that order.
   */
  public static String[] parseClip(String clip) {
    String trbl[] = new String[4];

    String[] param = clip.split("\\(");
    String[] params = param[1].split(",");
    if (params.length > 1) {
      trbl[0] = params[0].trim();
      trbl[1] = params[1].trim();
      trbl[2] = params[2].trim();
      trbl[3] = params[3].split("\\)")[0].trim();
    } else {
      // Someone's written in IE style.
      params = param[1].split(" ");
      trbl[0] = params[0].trim();
      trbl[1] = params[1].trim();
      trbl[2] = params[2].trim();
      trbl[3] = params[3].split("\\)")[0].trim();
    }
    return trbl;
  }

  /**
   * Checks if colour is transparent See issue #43
   * 
   * @param colour
   * @return
   */
  public static boolean isTransparent(String colour) {
    return colour.equals("transparent");
  }

  /**
   * Parse a string colour element into 3 individual integer rgb components
   * 
   * @param input string.
   * @return integer array of 3 elements representing rgb values in that order.
   * @throws Exception Allows us to throw exception if a colour cannot be parsed.
   */
  public static int[] parseColour(String hex) throws ColorException {

    int[] rgb = new int[4];

    //Assume alpha component is not set.
    rgb[3] = -1;
    try {
      // Lets first see if we are dealing with a Hex string (i.e.does the string
      // start with a #)
      if (hex.startsWith("#")) {
        hex = hex.substring(1).trim();
        if (hex.length() == 3) {
          rgb[0] = Integer.parseInt(hex.substring(0, 1) + hex.substring(0, 1),
              16);
          rgb[1] = Integer.parseInt(hex.substring(1, 2) + hex.substring(1, 2),
              16);
          rgb[2] = Integer.parseInt(hex.substring(2, 3) + hex.substring(2, 3),
              16);
        } else if (hex.length() == 6) {
          rgb[0] = Integer.parseInt(hex.substring(0, 2), 16);
          rgb[1] = Integer.parseInt(hex.substring(2, 4), 16);
          rgb[2] = Integer.parseInt(hex.substring(4, 6), 16);
        }
      } else {
        // OK, not a hex string, the lets see if it is rgb(a,b,c) or a
        // rgba(a,b,c)
        String[] split1 = hex.split("\\(");
        if (split1.length > 1) {
          String[] split2 = (split1[1]).split(",");
          if (split2.length > 2) {
            String one = split2[0].trim();
            String two = split2[1].trim();
            String thr = split2[2].trim();
            rgb[0] = Integer.parseInt(one);
            rgb[1] = Integer.parseInt(two);
            // Deal with alpha component if we have rgba(r,g,b,alpha)
            if (split1[0].equals("rgba")){
              rgb[2] = Integer.parseInt(thr);
              String fth = split2[3].trim();
              rgb[3] = (int)(Double.parseDouble(fth.split("\\)")[0])*100);
            } else{
              rgb[2] = Integer.parseInt(thr.split("\\)")[0]);
            }
          }
        } else {
          // OK, so it must be a named color, i.e. lightyellow / green etc etc
          // BUT, there are 150 of these implemented as standard across a
          // browser
          // AND, including support for all those will bloat the code, so we'll
          // return an exception
          throw new ColorException("Unable to parse colour: " + hex);
        }
      }
    } catch (ColorException e) {
      rgb[0] = 255;
      rgb[1] = 0;
      rgb[2] = 0;
      rgb[3] = 100;
      throw e;
    }
    return rgb;
  }
}
