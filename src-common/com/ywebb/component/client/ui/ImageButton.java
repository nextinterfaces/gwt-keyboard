package com.ywebb.component.client.ui;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * This is a button class with two states MouseUp and MouseDown. Bear in mind tablets don't necessarily have two sates,
 * but one - MouseDown only.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class ImageButton extends SimplePanel implements HasMouseDownHandlers, HasMouseUpHandlers {

  private final Image defaultImage;
  private boolean isEnabled = true;

  // private final Image overImage;

  private boolean isSelected = false;

  public ImageButton(ImageResource resource) {
    this(new Image(resource));
  }

  public ImageButton(Image imgDefault) {

    addStyleName("b_ImageButton");
    setSize(imgDefault.getWidth() + "px", imgDefault.getHeight() + "px");

    this.defaultImage = imgDefault;
    // this.overImage = imgOver;

    // this.defaultImage.unsinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);
    // this.overImage.unsinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);

    setWidget(defaultImage);
    // sinkEvents(Event.ONCLICK | Event.MOUSEEVENTS);

    // ImageSwapper imgSwapper = new ImageSwapper();
    // addMouseDownHandler(new MouseDownHandler() {
    // @Override
    // public void onMouseDown(MouseDownEvent event) {
    // setWidget(overImage);
    // }
    // });
    //
    // addMouseUpHandler(new MouseUpHandler() {
    // @Override
    // public void onMouseUp(MouseUpEvent event) {
    // setWidget(defaultImage);
    // }
    // });

    // addMouseOutHandler(imgSwapper);
    // // addMouseOverHandler(imgSwapper);
    // addClickHandler(imgSwapper);
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  public int getWidth() {
    return defaultImage.getOffsetWidth();
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  @Override
  public void onBrowserEvent(Event event) {
    if (!isEnabled()) {
      return;
    } else {
      super.onBrowserEvent(event);
    }
  }

  @Override
  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
    return addDomHandler(handler, MouseDownEvent.getType());
  }

  @Override
  public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
    return addDomHandler(handler, MouseUpEvent.getType());
  }

  public void setResource(ImageResource resource) {
    defaultImage.setResource(resource);
  }

  // public HandlerRegistration addClickHandler(ClickHandler handler) {
  // return addHandler(handler, ClickEvent.getType());
  // }
  //
  // @Override
  // public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
  // return addHandler(handler, MouseOutEvent.getType());
  // }

  //
  // @Override
  // public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
  // return addHandler(handler, MouseOverEvent.getType());
  // }

  // @Override
  // public void onBrowserEvent(Event event) {
  //
  // if (!enabled) {
  // return;
  // }
  //
  // // Suppress the propagation of the onClick event so that 2 events aren't
  // // fired.
  // if (DOM.eventGetType(event) != Event.ONCLICK) {
  // super.onBrowserEvent(event);
  // }
  //
  // switch (DOM.eventGetType(event)) {
  //
  // case Event.ONMOUSEOUT:
  // MouseOutEvent.fireNativeEvent(event, this);
  // break;
  //
  // case Event.ONMOUSEOVER:
  // MouseOverEvent.fireNativeEvent(event, this);
  // break;
  //
  // // The click listeners are fired as the result of an ONMOUSEUP event as
  // // IE has issues capturing the ONCLICK event.
  //
  // case Event.ONCLICK:
  // // TODO: Ensure that these are only fired if the
  // // onMouseDown was called on this button.
  // NativeEvent nativeClickEvent = Document.get().createClickEvent(1, 0, 0, 0,
  // 0, false, false,
  // false, false);
  //
  // ClickEvent.fireNativeEvent(nativeClickEvent, this);
  // break;
  //
  // default:
  // break;
  // }
  //
  // event.stopPropagation();
  // }
  //
  // public void reset() {
  // setWidget(defaultImage);
  // }

  // private class ImageSwapper implements MouseOutHandler, MouseOverHandler,
  // ClickHandler {
  //
  // @Override
  // public void onMouseOut(MouseOutEvent event) {
  // setWidget(defaultImage);
  // }
  //
  // @Override
  // public void onMouseOver(MouseOverEvent event) {
  // setWidget(overImage);
  // }
  //
  // @Override
  // public void onClick(ClickEvent event) {
  // setWidget(overImage);
  // }
  // };

}