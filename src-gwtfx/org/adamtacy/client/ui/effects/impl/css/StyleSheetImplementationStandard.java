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

import com.google.gwt.dom.client.Element;

/**
 * Deferred binding class allowing low level access to styles in the standard browsers
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class StyleSheetImplementationStandard {
  
  /**
   * Change the active Style Sheet for page
   * 
   * @param title
   */
  public native void changeStyle(String title)/*-{
        var i, a;
        for(i=0; (a = $doc.getElementsByTagName("link")[i]); i++) {
          if(a.getAttribute("rel").indexOf("style") != -1
             && a.getAttribute("title")) {
            a.disabled = true;
            if(a.getAttribute("title") == title) a.disabled = false;
          }
        }
      }-*/;

  /**
   * Low level access to determine the computed value of an element's property.
   * 
   * @param element
   * @param style
   * @return
   */
  public native String getComputedStyleJSNI(Element element, String styleProperty)/*-{
      		var c = $doc.defaultView.getComputedStyle(element, null);
      		if (c!=null){
      		  // Updated as part of Issue 122 
      		  // return c[styleProperty];
      		  return c.getPropertyValue(styleProperty);
      		}
      		return "";
      	}-*/;
  

  /**
   * Get the style value for a particular CSS selector. Only Gecko needs the
   * check for disabled below (o'wise it still picks styles from the default
   * style sheet regardless); but it is a wise check for other browsers too.
   * 
   * @param sN Name of the selector to search for.
   * @return
   */
  public native String getStyle(String sN)/*-{
      		var sS = $doc.styleSheets;
      		if(sS){
      			for(l1=0;l1<sS.length;l1++){
      			    if(!(sS[l1].disabled)){
      					var rs = sS[l1].cssRules;
      					for(l2=0;l2<rs.length;l2++){
      						var r = rs[l2].selectorText;
      						if (r == sN) {
      						   return rs[l2].cssText; 
      						}
      					}
      				}
      			}
      		}
      		return "";
      	}-*/;

  public String correctStyleProperty(String styleProperty) {
    //return styleProperty;
    return StyleImplementation.unCamelize(styleProperty);
  }

}
