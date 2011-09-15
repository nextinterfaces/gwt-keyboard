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

import org.adamtacy.client.ui.effects.core.NMorphStyle;
import org.adamtacy.client.ui.effects.events.EffectSteppingEvent;
import org.adamtacy.client.ui.effects.events.EffectSteppingHandler;
import org.adamtacy.client.ui.effects.impl.css.Rule;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class SlideBase extends NMorphStyle{

  
  public void setDisplayOutsideBounds(boolean newValue){
    displayOutsideBounds = newValue;
  }
  
  public SlideBase() {
    super(new Rule("slideStart{left: -400px; top: -400px; }"), new Rule("slideEnd{left: 0px; top: 0px; }"));
  }
  
  public SlideBase(Element el){
    this();
    addEffectElement(el);
  }
  
  
  public void setNewStartStyle(Rule start){
    registerProperties(start, theEnd, null,null);
    theStart = start;
    super.setUpEffect();
  }
  
  protected String newEnd;
  protected int height;
  protected int width;
  protected boolean displayOutsideBounds = false;
  
  Element effectElement;
  
  public void addEffectElement(Element el){
    super.addEffectElement(el);
    effectElement = effectElements.get(0);
  }
  
  protected void setUpEffect(final Command command){  
    if(thePanel!=null)
      thePanel.getElement().getStyle().setProperty("overflow", "hidden");
    // Need to call set up first, so that the panel gets hidden before being displayed
    // Dimensions are set up in Deferred Command below, which might take longer to execute than
    // the control back in the NEffect which displays the widget.  BY setting the initial effect to
    // large values in the constructor, we can then immediately hide this panel by position, then the
    // deferred command can do its work and place it where needed for sliding in.
    super.setUpEffect();
    DeferredCommand.addCommand(new Command() {
      public void execute() {
          height = effectElements.get(0).getOffsetHeight();
          width = effectElements.get(0).getOffsetWidth();
        command.execute();
      setNewStartStyle(new Rule(newEnd));
      }
    });
    // This change listener will allow the panel to be visible outside of bounding box (for example
    // we would like this on the elastic transition physics so that once the effect is greater than 1.0
    // we display, and below 1.0 we hide overshots.
    // However, since this is only really applicable to the elastic transition physics and would carry an overhead
    // for others, we allow a boolean to indicate if available or not.
    if((displayOutsideBounds)&&(thePanel!=null)){
      this.addEffectSteppingHandler(new EffectSteppingHandler(){
        public void onEffectStep(EffectSteppingEvent handler) {
          if(getProgressInterpolated()>1) 
            thePanel.getElement().getStyle().setProperty("overflow", "visible");
          else
            thePanel.getElement().getStyle().setProperty("overflow", "hidden");            
        }
      });
    }
  }
  
  public void tearDownEffect(){
    if(thePanel!=null){
      effectElements.get(0).getStyle().setProperty("top", thePanel.getElement().getStyle().getProperty("top"));
      effectElements.get(0).getStyle().setProperty("left", thePanel.getElement().getStyle().getProperty("left"));
      thePanel.getElement().getStyle().setProperty("overflow", "visible"); 
    }
  }
}
