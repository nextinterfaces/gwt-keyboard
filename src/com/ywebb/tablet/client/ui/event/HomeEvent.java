package com.ywebb.tablet.client.ui.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * An event notifying for Home button being clicked.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class HomeEvent extends GwtEvent<HomeHandler> {

  public static final GwtEvent.Type<HomeHandler> TYPE = new GwtEvent.Type<HomeHandler>();

  public HomeEvent() {
  }

  @Override
  protected void dispatch(HomeHandler handler) {
    handler.onExecute(this);
  }

  @Override
  public GwtEvent.Type<HomeHandler> getAssociatedType() {
    return TYPE;
  }

}
