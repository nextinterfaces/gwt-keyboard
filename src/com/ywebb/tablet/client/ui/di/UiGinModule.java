package com.ywebb.tablet.client.ui.di;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * A Dependency Injection module required to bind all IOC used classes.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class UiGinModule extends AbstractGinModule {

  protected void configure() {
    bind(TabletEventBus.class).in(Singleton.class);
    bind(UiSingleton.class).in(Singleton.class);
    // bind(MyRemoteService.class).toProvider(MyRemoteServiceProvider.class);
  }

}