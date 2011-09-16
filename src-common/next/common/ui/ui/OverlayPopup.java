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
package next.common.ui.ui;

import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * A overlay popup used to gray out the screen. Any popup which requires grey
 * out feauter on popup should derive from this class.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
 */
public class OverlayPopup extends PopupPanel {

  private final OverlayWrapper overlayWrapper;

  public OverlayPopup() {
    this(false, false);
  }

  public OverlayPopup(boolean autoHide, boolean modal) {
    super(autoHide, modal);
    overlayWrapper = new OverlayWrapper(this);
    // setAnimationEnabled(true);

    DOM.setStyleAttribute(this.getElement(), "position", "absolute");
  }

  // private OverlayPopup(boolean autoHide) {
  // this(autoHide, false);
  // }

  // @Override
  public void showOverlay() {
    overlayWrapper.showOverlay();
    // super.show();
  }

  public void hideOverlay() {
    overlayWrapper.hideOverlay();
  }

  public void addOverlayMouseDownHandler(MouseDownHandler handler) {
    overlayWrapper.addMouseDownHandler(handler);
  }

  // @Override
  // public void hide() {
  // super.hide();
  // // if (navigatable != null) {
  // // navigatable.onHide();
  // // }
  // }
}
