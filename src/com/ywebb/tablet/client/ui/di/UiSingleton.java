package com.ywebb.tablet.client.ui.di;

import com.google.inject.Inject;
import com.ywebb.component.client.ui.kbd.KeyboardPopup;

/**
 * This class is a workaround as GIN injector creates each time a new instance of the "injectable" classes. Yet for
 * classes such as FormPopup and KeyboardPopup we need to have ONLY one instance.
 * 
 * @author Atanas Roussev
 */
public class UiSingleton {

	private static KeyboardPopup keyboardPopup;

	private TabletEventBus tabletEventBus;

	@Inject
	public UiSingleton(TabletEventBus tabletEventBus) {
		this.tabletEventBus = tabletEventBus;
	}

	public KeyboardPopup getKeyboardPopup() {
		if (keyboardPopup == null) {
			keyboardPopup = new KeyboardPopup();
		}
		return keyboardPopup;
	}

}
