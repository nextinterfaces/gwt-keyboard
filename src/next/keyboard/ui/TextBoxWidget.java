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
package next.keyboard.ui;

import next.common.ui.ui.kbd.KeyboardTextBox;
import next.keyboard.ui.ui.di.TabletEventBus;
import next.keyboard.ui.ui.event.KeyboardOpenEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.SimplePanel;

public class TextBoxWidget extends SimplePanel {

	public TextBoxWidget(final TabletEventBus eventBus) {
		super();
		final boolean[] isFirstClick = { true };

		final KeyboardTextBox box = new KeyboardTextBox();

		box.setText("Click to open keyboard");
		box.setStyleName("b_textBoxBlank");

		box.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (isFirstClick[0]) {
					box.setText("");
					box.setStyleName("b_textBox");
					isFirstClick[0] = false;
				}
				eventBus.fireEvent(new KeyboardOpenEvent(box));
			}
		});
		add(box);
	}

}
