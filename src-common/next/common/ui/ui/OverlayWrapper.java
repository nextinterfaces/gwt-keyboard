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
 * @author Atanas Roussev ( http://nextinterfaces.com )
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