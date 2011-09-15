package com.ywebb.component.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * An utility class logging debug messages into DOM "id.debug" element.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class Logger {

  public static void debug(String html) {
    Element el = DOM.getElementById(ElementIds.DEBUG);
    if (el != null) {
//      DOM.setInnerHTML(DOM.getElementById(ElementIds.DEBUG), DOM.getInnerHTML(DOM
//          .getElementById(ElementIds.DEBUG))
//          + "<li>" + html);
    }
  }

  public static void debug(PopupPanel popup) {
    Element el = DOM.getElementById(ElementIds.DEBUG);
    if (el != null) {
      Logger.debug("offsetWidth:" + popup.getOffsetWidth() + ", offsetHeight:"
          + popup.getOffsetHeight() + "<br>" + ", ClientWidth" + Window.getClientWidth()
          + ", ClientHeight" + Window.getClientHeight() + "<br>ScrollLeft" + Window.getScrollLeft()
          + ", ScrollTop" + Window.getScrollTop());
    }
  }

}
