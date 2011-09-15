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
package org.adamtacy.client.ui.effects;

import org.adamtacy.client.ui.NEffectPanel;

// TODO adam provide an explanation of NStaticEffect
public abstract class NStaticEffect extends NEffect {

  final public void begin() {
  }

  @Override
  public void cancel() {
  }

  protected void dimensionalise() {
  }

  final public void effectStep() {
  }

  final protected void finished() {
  }

  /**
   * Associates the affect with the panel. This method is called by 
   * the onAttach() method of the associated NEffectPanel.
   * 
   * @param thePanel
   */
  @Override
  public void init(NEffectPanel thePanel) {
    this.thePanel = thePanel;
  }

  final public void interrupt() {
  }

  @Override
  final public void invert() {
  }

  final public void invert(boolean andReset) {
  }

  @Override
  final protected void onUpdate(double progress) {
  }

  @Override
  public void reset() {
  }

  final public void reverse() {
  }

  @Override
  public abstract void setUpEffect();

  final public void start() {
  }
  
  public void setPosition(double progress){}

}