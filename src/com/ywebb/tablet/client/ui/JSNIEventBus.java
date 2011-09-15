package com.ywebb.tablet.client.ui;

import com.ywebb.component.client.jsni.JSNIEvent;
import com.ywebb.tablet.client.ui.di.TabletEventBus;
import com.ywebb.tablet.client.ui.event.CSignInEvent;

public class JSNIEventBus {

  private TabletEventBus eventBus;

  public JSNIEventBus(TabletEventBus eventBus) {
    this.eventBus = eventBus;
  }

  public void fireEvent(String eventId) {
    if (JSNIEvent.SIGNUP.toString().equals(eventId)) {
      eventBus.fireEvent(new CSignInEvent());

    } else if (JSNIEvent.HOME.toString().equals(eventId)) {
      eventBus.fireEvent(new CSignInEvent());
    }
  }
}
