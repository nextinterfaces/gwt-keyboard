package com.ywebb.tablet.client.ui.event;

import com.google.gwt.event.shared.GwtEvent;
import com.ywebb.component.client.ui.kbd.KeyboardTextBox;

/**
 * An event notifying for drop form menu being clsoed.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class KeyboardOpenEvent extends GwtEvent<KeyboardOpenHandler> {

  public static final GwtEvent.Type<KeyboardOpenHandler> TYPE = new GwtEvent.Type<KeyboardOpenHandler>();

  private KeyboardTextBox textBox;

  public KeyboardOpenEvent(KeyboardTextBox textBox) {
    this.textBox = textBox;
  }

  @Override
  protected void dispatch(KeyboardOpenHandler handler) {
    handler.onExecute(this);
  }

  @Override
  public GwtEvent.Type<KeyboardOpenHandler> getAssociatedType() {
    return TYPE;
  }

  public KeyboardTextBox getTextBox() {
    return textBox;
  }

}
