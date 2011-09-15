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
import java.util.Iterator;

import org.adamtacy.client.ui.effects.impl.browsers.EffectImplementation;


/**
 * Encapsulates a CSS Rule, of the form: <br>
 * <br>
 * 
 * selector{ propertyName<i>1</i>:propertyValue<i>1</i>; --- ;
 * propertyName<i>n</i>:propertyValue<i>n</i>; }
 * 
 * @author Adam Tacy
 * @version 3.0
 */
public class Rule {

  /**
   * String representing the definition.
   */
  private String definition;
  
  /**
   * String representing the definition.
   */
  private String opacityFixedDefinition;

  /**
   * String representing the original input.
   */
  private String original;

  /**
   * HashMap containing all the properties found in the rule.
   * 
   * Note it is populated in order that the rule is defined, hashed against
   * property names, i.e. if a particular property appears multiple times in a
   * rule, the only the last instance is remembered.
   * 
   */
  private HashMap<String, Property> properties;

  /**
   * String representing the selector part of the rule.
   */
  private String selector;

  /**
   * Constructor.
   * 
   * @param s String representing the CSS rule.
   */
  public Rule(String s) {
    // Store the original value in case requested later.
    this.original = s;
    // Start parsing by splitting around the { symbol
    String[] firstParse = s.split("\\{");
    // We should always get 2 elements here, so let's assert that.
    assert (firstParse.length == 2);

    // The first element of the parse equals the selector name
    selector = firstParse[0].trim();
    // The second element is the rule definition.
    definition = firstParse[1];
    // We also parse that definition now too (first, let's chop off the trailing
    // curly bracket
    definition = definition.substring(0, definition.length() - 1);
    // Now we're left with a set of property/value pairs separated by
    // semi-colons.
    String[] secondParse = definition.split(";");
    // For each property Pair, create a new Property object and store in the
    // hashmap
    properties = new HashMap<String, Property>();
    for (int loop = 0; loop < secondParse.length ; loop++) {
      String property = secondParse[loop].trim();
      if(!property.equals("")) addProperty(new Property(property));
    }
  }

  /**
   * Get the String representation of the rule definition
   * 
   * @return
   */
  public String getDefinition() {
    if(opacityFixedDefinition==null){
      for(Iterator<String> t = properties.keySet().iterator();t.hasNext();){
        String val = t.next();
        if (val.equals("opacity")){
          opacityFixedDefinition += EffectImplementation.getOpacityText(new Double(properties.get(val).getValue()).doubleValue());
        } else {
          opacityFixedDefinition += val + ":" + properties.get(val).getValue()+properties.get(val).getUnits() + "; ";
        }
      }
    }
    return opacityFixedDefinition;
  }

  /**
   * Get the hashmap of properties for this rule.
   * 
   * @return HashMap containing a set of Property objects representing the rule.
   */
  public HashMap<String, Property> getProperties() {
    return properties;
  }

  /**
   * Get the String representation of the selector.
   * 
   * @return
   */
  public String getSelector() {
    return selector;
  }

  /**
   * Get the String representation of the whole rule.
   */
  @Override
  public String toString() {
    return original;
  }

  /**
   * Allow the ability to add a new Property to a Rule
   * (to support issue 115)
   * @param replaceEnd
   */
  public void addProperty(Property property) {
    properties.put(property.getName(), property);
  }
}
