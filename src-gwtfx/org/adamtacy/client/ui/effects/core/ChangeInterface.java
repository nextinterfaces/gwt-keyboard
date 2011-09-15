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

import com.google.gwt.dom.client.Element;

public interface ChangeInterface {
  public Object performStep(Element effectElement, String styleComponentToChange,
      double progress);

  public void reset();

  public void setUp(Element effectElement, String styleComponentToChange,
      double switchFrameNumber);

  public void tearDownEffect(Element effectElement);
}
