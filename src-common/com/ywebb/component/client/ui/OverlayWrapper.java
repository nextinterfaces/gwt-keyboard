package com.ywebb.component.client.ui;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.Fade;
import org.adamtacy.client.ui.effects.impl.NShow;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * A helper class for the overlay popup.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class OverlayWrapper {

  class OverlayPanel extends PopupPanel implements HasMouseDownHandlers {
    OverlayPanel() {
      super(false, false);
      removeStyleName("gwt-PopupPanel");
      addStyleName("overlay-bg");
    }

    @Override
    public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
      return addDomHandler(handler, MouseDownEvent.getType());
    }
  }

  private final static Integer OVERLAY_Z_INDEX = 90;
  private final static Integer OVERLAY_TRANSPARENCY = 50;

  private OverlayPanel overlay;

  private PopupPanel popup;

  public OverlayWrapper(PopupPanel popupPanel) {

    this.popup = popupPanel;
    popup.removeStyleName("gwt-PopupPanel");
    popup.addStyleName("overlay-popup");

    // popupPanel.addCloseHandler(new CloseHandler<PopupPanel>() {
    // @Override
    // public void onClose(CloseEvent<PopupPanel> event) {
    // hideOverlay();
    // }
    // });

    overlay = new OverlayPanel();
  }

  public void addMouseDownHandler(MouseDownHandler handler) {
    overlay.addMouseDownHandler(handler);
  }

  public void hideOverlay() {
    // overlay.hide();
    Fade eff = new Fade(overlay.getElement());
    eff.addEffectCompletedHandler(new EffectCompletedHandler() {
      @Override
      public void onEffectCompleted(EffectCompletedEvent event) {
        DOM.setStyleAttribute(overlay.getElement(), "zIndex", "-" + OVERLAY_Z_INDEX);
      }
    });
    eff.setStartOpacity(OVERLAY_TRANSPARENCY);
    eff.setEndOpacity(0);
    eff.setDuration(0.3);
    eff.play();
  }

  public void showOverlay() {
    DOM.setStyleAttribute(overlay.getElement(), "height", Window.getClientHeight() + "px");
    DOM.setStyleAttribute(overlay.getElement(), "zIndex", OVERLAY_Z_INDEX.toString());
    overlay.show();

    NShow eff = new NShow(overlay.getElement());
    eff.setStartOpacity(0);
    eff.setEndOpacity(OVERLAY_TRANSPARENCY);
    eff.setDuration(0.3);
    eff.play();
  }

}