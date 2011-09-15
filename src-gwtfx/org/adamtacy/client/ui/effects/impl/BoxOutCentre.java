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
 * Effect makes the element disappear into a box in the centre
 * 
 * @author Adam Tacy
 * @version 3.0
 *
 */
public class BoxOutCentre extends ClipBase {

  public BoxOutCentre() {
    super();
  }

  public BoxOutCentre(String startClip, String endClip) {
    super(startClip, endClip);
  }
  
  public BoxOutCentre(Element el) {
    super(el);
  }

  public BoxOutCentre(Element el, String startClip, String endClip) {
    super(el, startClip, endClip);
  }

  @Override
  public void setUpEffect() {
     super.setUpEffect(new Command() {
      public void execute() {
        int halfWidth = new Double(Math.ceil(width / 2)).intValue() + 1;
        int halfHeight = new Double(Math.ceil(height / 2)).intValue() + 1;
        setStartClip(
            "rect(" + halfHeight + "px," + halfWidth + "px," + halfHeight
                + "px," + halfWidth + "px)");
        setEndClip(
            "rect(0px," + width + "px," + height + "px,0px)");
      }
    });
  }
}