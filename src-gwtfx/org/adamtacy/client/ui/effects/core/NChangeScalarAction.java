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

import org.adamtacy.client.ui.effects.impl.browsers.EffectImplementation;
import org.adamtacy.client.ui.effects.impl.css.StyleImplementation;

import com.google.gwt.dom.client.Element;

public class NChangeScalarAction implements ChangeInterface {

  protected StepFunctionInterface assignedStepFunction = new LinearStep();

  /** The current number */
  protected double current;

  /** The delta between start and end number */
  protected double delta;

  /** The end number */
  protected double end = 100;

  boolean isMoveable = false;

  boolean isOpacity = false;

  /** The start number */
  protected double start = 0;
  
  /** The style we will be changing in a scalar manner **/
  protected String styleToChange = "";
  
  /** The unit of the style we will be changing **/
  protected String unitOfStyleComponent = "";

  public NChangeScalarAction() {
  }

  public NChangeScalarAction(String styleStart, String styleEnd) {
    this.setStartValue(styleStart);
    this.setEndValue(styleEnd);
  }
  
  public StepFunctionInterface getStepFunction(){
    return assignedStepFunction;
  }

  public String getUnitOfStyleComponent() {
    return unitOfStyleComponent;
  }

  public double getValue(double curr) {
    return start + (curr * (end - start));
  }
  
  public Double performStep(Element effectElement, String styleComponentToChange,
      double progress) {
    double val = assignedStepFunction.getPosition(start, end, progress);
    if (!isOpacity)
      EffectImplementation.changeNumericalStyleAttribute(effectElement,
          styleToChange, val, unitOfStyleComponent);
    else
      EffectImplementation.setOpacity(effectElement, val);
    return val;
  }
  
  /** Reset the effect */
  public void reset() {
  }

  /** Change the ending number */
  public void setEndValue(String newEnd) {
    unitOfStyleComponent = StyleImplementation.getUnits(newEnd);
    end = Double.parseDouble(StyleImplementation.getValue(newEnd,
        unitOfStyleComponent));
  }

  /** Change the starting number */
  public void setStartValue(String newStart) {
    unitOfStyleComponent = StyleImplementation.getUnits(newStart);
    start = Double.parseDouble(StyleImplementation.getValue(newStart,
        unitOfStyleComponent));
  }

  /**
   * Set the function that is used for stepping - by default this is the "identity" function, 
   * i.e. a linear progression from start to end.
   * This should not be confused with transition physics and is most likely to be used with 
   * movement effects that need to move objects in a non straight line.
   * @param newStepFunction
   */
  public void setStepFunction(StepFunctionInterface newStepFunction){
    assignedStepFunction = newStepFunction;
  }

  public void setUp(Element effectElement, String styleComponentToChange,
      double switchFrameNumber) {
    styleToChange = styleComponentToChange;
    if (styleComponentToChange.equals("opacity"))
      isOpacity = true;
    if (styleComponentToChange.equals("left")||styleComponentToChange.equals("top")||styleComponentToChange.equals("right")||styleComponentToChange.equals("bottom")){
      isMoveable = true;
      EffectImplementation.setMoveable(effectElement, true);
    }
    this.performStep(effectElement, styleComponentToChange, 0.0);
  }

  public void tearDownEffect(Element effectElement) {
    if (isMoveable) {
      effectElement.getStyle().setPropertyPx("top", effectElement.getParentElement().getAbsoluteTop());
      effectElement.getStyle().setPropertyPx("left", effectElement.getParentElement().getAbsoluteLeft());
      EffectImplementation.setMoveable(effectElement, false);
    }
    if (isOpacity) {
      EffectImplementation.setOpacity(effectElement, 100.0);
    }
  }

  public String toString(){
    return styleToChange + ": " + start + "->" + end;
  }
}
