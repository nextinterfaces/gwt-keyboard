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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Element;

/**
 * Simple blind down effect where image is revealed from top down.
 * 
 * @author Adam Tacy
 * @version 3.0
 * 
 */
public class BlindDown extends ClipBase {

  public BlindDown() {
    super();
  }

  /**
   * Constructor that sets the start and ending clip positions.
   * 
   * @param startClip A newly defined starting clip position (use CSS defn, i.e. rect(top, right, bottom, left))
   * @param endClip A newly defined ending clip position (use CSS defn)
   */
  public BlindDown(String startClip, String endClip) {
    super(startClip, endClip);
  }
  
  public BlindDown(Element el) {
    super(el);
  }

  public BlindDown(Element el, String startClip, String endClip) {
    super(el, startClip, endClip);
  }

  /**
   * Set up the effect so that the 
   */
  @Override
  public void setUpEffect() {
     super.setUpEffect(new Command() {
      public void execute() {
        setStartClip("rect(0px," + width + "px,0px,0px)");
        setEndClip(
            "rect(0px," + width + "px," + height + "px,0px)");
      }
    });
  }
}
