package com.ywebb.component.client.ui;

import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * A overlay popup used to gray out the screen. Any popup which requires grey
 * out feauter on popup should derive from this class.
 * <p>
 * 
 * @author Atanas Roussev
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
