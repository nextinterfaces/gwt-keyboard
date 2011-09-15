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

import com.google.gwt.core.client.GWT;

/**
 * Encapsulates a CSS property/value pair. Expects to be created using a String
 * input of the form: <br>
 * <br>
 * 
 * propertyName : propertyValue
 * 
 * <br>
 * 
 * (note the missing end semi-colon in the above, which is used to delineate
 * between individual property/value pairs in a rule definition)
 * 
 * @author Adam Tacy
 * @version 3.0 
 */
public class Property {

  /**
   * Name of the property
   */
  private String propertyName;
  
  /**
   * Value of the property
   */
  private String propertyValue;
  
  /**
   * Unit of the property
   */
  private String units;

  /** 
   * Consructor
   * 
   * @param s String representation of the property, e.g.  background-color: #ffcc11;
   */
  public Property(String s) {
    try{
      String[] split = s.split(":");

      // Should always get 2 elements here
      //assert (split.length == 2);

      // Camelize the name as that is what JavaScript can use
      propertyName = StyleImplementation.camelize(split[0].trim());

      // Trim the value so we have a clean String to work with
      String styleCompleteResult = split[1].trim();

      // Get the units that this property value has
      units = StyleImplementation.getUnits(styleCompleteResult);
      // Get the non-unit value
      propertyValue = StyleImplementation.getValue(styleCompleteResult, units);
      } catch (Exception e){
        GWT.log("Error in the Property: "+s+" (have you forgotten the trailing semicolon?)", null);
      }
  }

  /**
   * Get string representation of the property name.
   * 
   * @return
   */
  public String getName() {
    return propertyName;
  }

  /**
   * Get string representation of the value's unit.
   * 
   * @return
   */
  public String getUnits() {
    return units;
  }

  /**
   * Get string representation of the property value.
   * 
   * @return
   */
  public String getValue() {
    return propertyValue;
  }
  
  /**
   * Get string representation of the property value with units attached.
   * 
   * @return
   */
  public String getUnitizedValue(){
    return propertyValue + units;
  }

  /**
   * Set property value.
   * 
   * @param newValue The new value for the property.
   */
  public void setValue(String newValue){
    propertyValue = newValue;
  }
  
  /**
   * Get string representation of the property/value pair.
   * 
   * @return
   */
  @Override
  public String toString() {
    return propertyName + " : " + propertyValue + units;
  }
}
