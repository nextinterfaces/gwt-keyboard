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
package org.adamtacy.client.ui;

import java.util.Vector;

import org.adamtacy.client.ui.NEffectPanel;
import org.adamtacy.client.ui.effects.NEffect;
import org.adamtacy.client.ui.effects.impl.ReflectImage;
import org.adamtacy.client.ui.effects.impl.Resize;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasLoadHandlers;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;


/**
 * Simple class for creating and handling a Reflected Image.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class ReflectedImage extends Composite implements HasClickHandlers,
    HasLoadHandlers {

  NEffectPanel ep;
  boolean previous = false;
  Image theImage;

  ReflectImage theReflection;

  public ReflectedImage() {
    theImage = new Image();
    NEffectPanel ep = new NEffectPanel();
    ep.add(theImage);
    theReflection = new ReflectImage();
    ep.addEffect(theReflection);
    initWidget(ep);
    setForResizeEffect();
  }

  public ReflectedImage(Image img) {
    ep.add(img);
    theReflection = new ReflectImage();
    ep.addEffect(theReflection);
    initWidget(ep);
    setForResizeEffect();
  }

  public ReflectedImage(String image) {
    this();
    setUrl(image);
    theImage.addLoadHandler(new LoadHandler() {
      public void onLoad(LoadEvent event) {
        theReflection.play();     
      }
    });
    setForResizeEffect();
  }

  
  public HandlerRegistration addLoadHandler(LoadHandler handler){
    return theImage.addLoadHandler(handler);
  }

  public void reapply() {
    theReflection.reset();
  }


  public void removeEffect(NEffect theEffect) {
    ep.removeEffect(theEffect);
  }

  public void setHeight(String height) {
    theReflection.getReflectedImage().setHeight(height);
  }

  public void setReflectionDepth(int d) {
    theReflection.setHeight(d);
    reapply();
  }
  
  /**
   * Add the CANVAS tag to the list of DOM elements that a resize effect
   * will manage.
   */
  public void setForResizeEffect(){
    Vector<String> t = new Vector<String>();
    t.add("CANVAS");
    Resize.addToTriggerDOM(t);
  }

  public void setReflectionOpacity(int d) {
    theReflection.setOpacity(d);
    reapply();
  }

  public void setSize(String width, String height) {
    theReflection.getReflectedImage().setSize(width, height);
    reapply();
  }

  public void setStylePrimaryName(String name) {
    theReflection.getReflectedImage().setStylePrimaryName(name);
  }

  public void setUrl(String url) {
    theImage.setUrl(url);

    if (previous)
      theImage.addLoadHandler(new LoadHandler() {
        public void onLoad(LoadEvent event) {
          reapply();          
        }
      });
    previous = true;
  }

  public void setWidth(String width) {
    theReflection.getReflectedImage().setWidth(width);
    reapply();
  }

  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return theImage.addClickHandler(handler);
  }
}
