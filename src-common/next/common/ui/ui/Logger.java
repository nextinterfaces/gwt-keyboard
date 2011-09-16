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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * An utility class logging debug messages into DOM "id.debug" element.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
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
