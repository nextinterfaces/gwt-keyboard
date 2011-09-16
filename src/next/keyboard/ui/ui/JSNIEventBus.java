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
package next.keyboard.ui.ui;

import next.common.ui.jsni.JSNIEvent;
import next.keyboard.ui.ui.di.TabletEventBus;
import next.keyboard.ui.ui.event.CSignInEvent;


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
