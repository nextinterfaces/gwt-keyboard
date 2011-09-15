package com.ywebb.component.client.jsni;

public class JSInvoker {

  public static native void fireJSNIEventBus(String event) /*-{
    $wnd.parent.window.__fireJSNIEventBus(event);
  }-*/;

}
