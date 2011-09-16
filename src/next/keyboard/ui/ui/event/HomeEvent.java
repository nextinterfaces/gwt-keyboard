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
package next.keyboard.ui.ui.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * An event notifying for Home button being clicked.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
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
