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
/**
 * Implementation inspired by, amongst other references,
 * http://www.digitalia.be/software/reflectionjs-for-mootools
 * 
 */
package org.adamtacy.client.ui.effects.impl;

import org.adamtacy.client.ui.effects.NStaticEffect;
import org.adamtacy.client.ui.effects.impl.browsers.EffectImplementation;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ReflectImage extends NStaticEffect {
  
  public int getGap() {
    return gap;
  }

  public int getReflectionHeight() {
    return height;
  }

  public void setGap(int newGap) {
    gap = newGap;
  }

  /**
   * Set the Effect's height.
   * 
   * @param newHeight
   */
  public void setHeight(int newHeight) {
    height = newHeight;
  }

  /**
   * Set the Effect's opacity.
   * 
   * @param newHeight
   */
  public void setOpacity(int newOpacity) {
    opacity = newOpacity;
    nOp = ((double) opacity) / 100;
  }

  protected int gap = 10;
  protected int height = 33;
  protected int opacity = 50;
  double nOp = ((double) opacity) / 100;


  Image originalImage;



  private void applyEffect() {
    if(!setUp)EffectImplementation.reflectImage(thePanel, height, nOp, gap, setUp);
    thePanel.getWidget().setVisible(true);
    setUp = true;
  }


  public Image getReflectedImage() {
    return EffectImplementation.getReflectedImage(thePanel);
  }

  @Override
  public void play() {
    applyEffect();
  }
  
  public void cancel(){
    tearDownEffect();
  }
  
  boolean setUp = false;

  @Override
  public void reset() {
    super.reset();
    applyEffect();
  }

  @Override
  public void setUpEffect() {
    Widget w = thePanel.getPanelWidget();
    if (w instanceof Image) {
      originalImage = (Image) w;
      applyEffect();
    } else
      throw new RuntimeException("Can only apply reflection effect to an image");
  }

  @Override
  public void tearDownEffect() {
    EffectImplementation.unReflectImage(thePanel);
    setUp = false;
  }

}
