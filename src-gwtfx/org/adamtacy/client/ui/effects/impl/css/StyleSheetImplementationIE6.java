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
 * Style sheet implementation class to cope with Internet Explorer's
 * differences.
 * 
 * @author Adam Tacy
 * @version 3.0
 *   
 */
public class StyleSheetImplementationIE6 extends
    StyleSheetImplementationStandard {
  
  @Override
  public String correctStyleProperty(String styleProperty) {
    return StyleImplementation.camelize(styleProperty);
    //return StyleImplementation.unCamelize(styleProperty);
  }

  @Override
  public native String getComputedStyleJSNI(Element element,
      String styleProperty)/*-{
      		var c = element.currentStyle;
      		return c[styleProperty];
      	}-*/;

  @Override
  public native String getStyle(String styleName)/*-{
      	var tR = "";
        var s = $doc.styleSheets;
        for (loop1 = 0; loop1<s.length; loop1++){
          if (!s[loop1].disabled) {
             var r = s[loop1].rules;
             for(loop2 = 0; loop2 < r.length; loop2++){
                if (r[loop2].selectorText == styleName){
                   tR = styleName + " { " + r[loop2].style.cssText + " }";
                }
             }
          }
        }
        return tR;
      }-*/;
}
