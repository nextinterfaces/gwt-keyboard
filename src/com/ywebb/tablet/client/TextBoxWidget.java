package com.ywebb.tablet.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.ywebb.component.client.ui.kbd.KeyboardTextBox;
import com.ywebb.tablet.client.ui.di.TabletEventBus;
import com.ywebb.tablet.client.ui.event.KeyboardOpenEvent;

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
