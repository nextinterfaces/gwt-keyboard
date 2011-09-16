/*
 * Copyright 2011 Vancouver Ywebb Consulting Ltd
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
package next.common.ui.ui.kbd;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A single key widget with MouseDown and MouseUp listeners.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
 */
public class KeyboardPanel extends SimplePanel implements HasMouseDownHandlers, HasMouseUpHandlers {

  private boolean isShiftKey = false;

  public KeyboardPanel(final Image upImg, final Image downImg) {
    super();
    setWidget(upImg);

    addMouseDownHandler(new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        if (!isShiftKey()) {
          setWidget(downImg);
        }
      }
    });

    addMouseUpHandler(new MouseUpHandler() {
      @Override
      public void onMouseUp(MouseUpEvent event) {
        if (!isShiftKey()) {
          setWidget(upImg);
        }
      }
    });
  }

  public boolean isShiftKey() {
    return isShiftKey;
  }

  public void setShiftKey(boolean isShiftKey) {
    this.isShiftKey = isShiftKey;
  }

  @Override
  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
    return addDomHandler(handler, MouseDownEvent.getType());
  }

  @Override
  public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
    return addDomHandler(handler, MouseUpEvent.getType());
  }

}
