package com.ywebb.tablet.client.ui.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * A event notifying for signup being completed(account created).
 * <p>
 * 
 * @author Atanas Roussev
 */
public class CSignInEvent extends GwtEvent<CSignInHandler> {

  public static final GwtEvent.Type<CSignInHandler> TYPE = new GwtEvent.Type<CSignInHandler>();

  public CSignInEvent() {
  }

  @Override
  protected void dispatch(CSignInHandler handler) {
    handler.onExecute(this);
  }

  @Override
  public GwtEvent.Type<CSignInHandler> getAssociatedType() {
    return TYPE;
  }

}
