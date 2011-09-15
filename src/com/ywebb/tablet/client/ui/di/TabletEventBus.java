package com.ywebb.tablet.client.ui.di;

import com.google.gwt.event.shared.HandlerManager;

/**
 * A master TabletEventBus object listening for events and nitfying the registered
 * handlers.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class TabletEventBus extends HandlerManager {

  public TabletEventBus() {
    super(null);
  }

}
