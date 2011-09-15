package com.ywebb.tablet.client.ui.di;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

/**
 * A Dependency Injection holding tablet IOC classes.
 * <p>
 * 
 * @author Atanas Roussev
 */
@GinModules(UiGinModule.class)
public interface UiGinjector extends Ginjector {

  public final static UiGinjector INSTANCE = GWT.create(UiGinjector.class);

  UiSingleton getUiSingleton();

  TabletEventBus getEventBus();

//  TabletPanel getTabletPanel();

}
