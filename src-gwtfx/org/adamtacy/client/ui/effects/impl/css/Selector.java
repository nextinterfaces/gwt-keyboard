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

/**
 * A class to represent a Selector and rule in a style sheet.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class Selector {

  /**
   * the Rule the selector points to
   */
  private Rule ruleDefinition;
  
  /**
   * The selector name for future reference
   */
  private String selector;

  /**
   * Constructor taking selector name as a string
   * @param selector
   */
  public Selector(final String selector) {
    // Save the name for later use
    this.selector = selector;
    // Get the rule from a loaded style sheet
    String theRule = StyleImplementation.styleSheetImpl.getStyle(selector).toString();
    // We should have a value here - if not, throw a runtime exception.
    if (theRule==null|| theRule.equals(""))
      throw new RuntimeException("GWT-FX: Unable to find CSS rule matching: " + selector);
    else 
      // Save the Rule
      ruleDefinition = new Rule(theRule);
  }

  /**
   * Get the CSS Rule
   * @return the CSS Rule
   */
  public Rule getRuleDefinition() {
    return ruleDefinition;
  }
  
/**
 * Get the selector name.
 * @return the selector name
 */
  public String getSelector() {
    return selector;
  }

  @Override
  public String toString() {
    return ruleDefinition.toString();
  }
}
