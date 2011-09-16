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

import next.common.ui.ui.Logger;
import next.common.ui.ui.kbd.KeyboardPopup;
import next.common.ui.ui.kbd.KeyboardTextBox;
import next.keyboard.ui.ui.di.TabletEventBus;
import next.keyboard.ui.ui.di.UiGinjector;
import next.keyboard.ui.ui.event.KeyboardOpenEvent;
import next.keyboard.ui.ui.event.KeyboardOpenHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;

/**
 * This class is the main entry point for tablet module.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
 */
public class Main implements EntryPoint {

	public void onModuleLoad() {
		initModuleLoad();
	}

	void initModuleLoad() {
		final RootPanel rootPanel = RootPanel.get("root");
		rootPanel.setVisible(false);

		final UiGinjector ctx = UiGinjector.INSTANCE;

		// init and make it visible
		// ctx.getUiSingleton().getWalletLeftMenu().show();

		rootPanel.add(new TextBoxWidget(ctx.getEventBus()));

		rootPanel.setVisible(true);

		RootPanel loadingPanel = RootPanel.get("loading");
		loadingPanel.setVisible(false);

		// Used for debuggin only
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int cH = Window.getClientHeight();
				int cW = Window.getClientWidth();

				int scH = Window.getScrollTop();
				int scL = Window.getScrollLeft();
				Logger.debug("clientHeight:" + cH + ", clientWidth:" + cW + "| scrollTop:" + scH + ", scrollLeft:"
						+ scL);
			}
		});

		listenEventUpdates(ctx.getEventBus());

		// ctx.getEventBus().fireEvent(new LsShowSportsEvent(true));
		// RootPanel.get("root").setVisible(false);
	}

	void listenEventUpdates(TabletEventBus eventBus) {
		eventBus.addHandler(KeyboardOpenEvent.TYPE, new KeyboardOpenHandler() {
			@Override
			public void onExecute(KeyboardOpenEvent e) {
				KeyboardTextBox box = e.getTextBox();
				UiGinjector ctx = UiGinjector.INSTANCE;
				final KeyboardPopup kb = ctx.getUiSingleton().getKeyboardPopup();
				kb.bind(box);
				box.setSelected(true);

				kb.setPopupPositionAndShow(new PositionCallback() {
					@Override
					public void setPosition(int offsetWidth, int offsetHeight) {
						int leftStart = (int) (offsetWidth / 2.0);
						int topStart = Window.getClientHeight();
						int topEnd = Window.getClientHeight() - offsetHeight;
						kb.openAnimated_setPopupPosition(leftStart, leftStart, topStart, topEnd);
					}
				});
			}
		});
	}

}
