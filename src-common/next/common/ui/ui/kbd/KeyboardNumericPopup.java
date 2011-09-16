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
package next.common.ui.ui.kbd;

import next.common.ui.ui.ElementIds;
import next.common.ui.ui.Logger;
import next.common.ui.ui.MiscUtils;
import next.common.ui.ui.OverlayWrapper;

import org.adamtacy.client.ui.effects.core.NMorphStyle;
import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.css.Rule;
import org.adamtacy.client.ui.effects.transitionsphysics.EaseInTransitionPhysics;
import org.adamtacy.client.ui.effects.transitionsphysics.EaseOutTransitionPhysics;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

/**
 * This is a numeric keyboard widget implementation.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
 */
public class KeyboardNumericPopup extends PopupPanel {

  private KeyboardTextBox textBox;
  private static String[] KEYS_QWERTY = { "7", "8", "9", "0", "", "", "4", "5", "6", "BS", "", "",
      "1", "2", "3", "DO" };

  public static enum Step {
    HIDDEN, MOVING, OPENED
  }

  private Step step = Step.HIDDEN;
  private boolean showOverlay;

  private final OverlayWrapper overlayWrapper;

  @Inject
  public KeyboardNumericPopup() {
    super(false, false);
    overlayWrapper = new OverlayWrapper(this);

    DOM.setStyleAttribute(this.getElement(), "position", "absolute");

    setStyleName("b_keyboardNumeric");
    getElement().setId(ElementIds.KEYBOARD_NUM);

    FlowPanel flowPanel = new FlowPanel();
    add(flowPanel);
    buildKeyboard(flowPanel);

    addOverlayMouseDownHandler(new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        closeAnimated();
      }
    });
  }

  public void showOverlay() {
    if (showOverlay) {
      overlayWrapper.showOverlay();
    }
  }

  public void hideOverlay() {
    if (showOverlay) {
      overlayWrapper.hideOverlay();
    }
  }

  public void addOverlayMouseDownHandler(MouseDownHandler handler) {
    overlayWrapper.addMouseDownHandler(handler);
  }

  public void setShowOverlay(boolean showOverlay) {
    this.showOverlay = showOverlay;
  }

  private void buildKeyboard(FlowPanel flowPanel) {

    final boolean[] isShiftOn = { false };

    final KeyboardPanel[] shiftKey = new KeyboardPanel[1];
    for (int i = 0; i < KEYS_QWERTY.length; i++) {
      final String key = KEYS_QWERTY[i];
      final Image[] images = MiscUtils.getImage(key);
      final KeyboardPanel btn = new KeyboardPanel(images[0], images[1]);

      if ("".equals(key)) {
        btn.addStyleName("b_key_emp");

      } else if ("SP".equals(key)) {
        btn.addStyleName("b_key_space");
        btn.addMouseDownHandler(new MouseDownHandler() {
          @Override
          public void onMouseDown(MouseDownEvent event) {
            getTextBox().setText(getTextBox().getText() + " ");
          }
        });

      } else if (" ".equals(key)) {
        btn.addStyleName("b_key_emp2");

      } else if ("#0".equals(key)) {
        btn.addStyleName("b_key_special0");

      } else if ("#1".equals(key)) {
        btn.addStyleName("b_key_special1");

      } else if ("#2".equals(key)) {
        btn.addStyleName("b_key_special2");

      } else if ("SH".equals(key)) {
        btn.setShiftKey(true);
        shiftKey[0] = btn;
        btn.addStyleName("b_key_shift");
        btn.addMouseDownHandler(new MouseDownHandler() {
          @Override
          public void onMouseDown(MouseDownEvent event) {
            isShiftOn[0] = !isShiftOn[0];
            if (isShiftOn[0]) {
              btn.setWidget(images[1]);

            } else {
              btn.setWidget(images[0]);
            }
          }
        });

      } else if ("BS".equals(key)) {
        btn.addStyleName("b_key_bs");
        btn.addMouseDownHandler(new MouseDownHandler() {
          @Override
          public void onMouseDown(MouseDownEvent event) {
            try {
              String ss = getTextBox().getText();
              ss = ss.substring(0, ss.length() - 1);
              getTextBox().setText(ss);
            } catch (StringIndexOutOfBoundsException e) {
              // normal behavior
            }
          }
        });

      } else if ("EN".equals(key)) {
        btn.addStyleName("b_key_en");
        btn.addMouseDownHandler(new MouseDownHandler() {
          @Override
          public void onMouseDown(MouseDownEvent event) {
            btn.setWidget(images[0]);
            closeAnimated();
          }
        });

      } else if ("DO".equals(key)) {
        btn.addStyleName("b_key_done");
        btn.addMouseDownHandler(new MouseDownHandler() {
          @Override
          public void onMouseDown(MouseDownEvent event) {
            btn.setWidget(images[0]);
            closeAnimated();
          }
        });

      } else {
        btn.addStyleName("b_key");
        btn.addMouseDownHandler(new MouseDownHandler() {

          private Image upImage = MiscUtils.getImage("SH")[0];

          @Override
          public void onMouseDown(MouseDownEvent event) {
            String input = getInput(key, isShiftOn[0]);
            if (isShiftOn[0]) {
              input = input.toUpperCase();
              isShiftOn[0] = false;
              shiftKey[0].setWidget(upImage);
            }
            getTextBox().setText(getTextBox().getText() + input);
          }
        });
      }
      flowPanel.add(btn);
    }
  }

  public Step getStep() {
    return step;
  }

  public void setStep(Step step) {
    this.step = step;
  }

  public KeyboardTextBox getTextBox() {
    return textBox;
  }

  public void bind(KeyboardTextBox textBox) {
    if (this.textBox != null) {
      KeyboardTextBox prevoiusBox = (KeyboardTextBox) this.textBox;
      prevoiusBox.setSelected(false);
    }
    this.textBox = textBox;
  }

  public void unbind() {
    this.textBox = null;
  }

  private String getInput(String key, boolean isShiftOn) {
    if ("AT".equals(key)) {
      return (isShiftOn) ? "@" : ".";

    } else if ("DASH".equals(key)) {
      return (isShiftOn) ? "-" : "_";

    } else {
      return key;
    }
  }

  public void closeAnimated() {
    int leftStart = getAbsoluteLeft();
    int leftEnd = leftStart;
    int topStart = getAbsoluteTop();
    int topEnd = Window.getClientHeight();
    closeAnimated(leftStart, leftEnd, topStart, topEnd);
  }

  public void closeAnimated(int leftStart, int leftEnd, int topStart, int topEnd) {

    if (step != Step.OPENED) {
      return;
    }
    NMorphStyle eff = new NMorphStyle(new Rule("start{position: absolute; left: " + leftStart
        + "px; top: " + topStart + "px; opacity: 100%;}"), new Rule(
        "end{position: absolute; left: " + leftEnd + "px; top: " + topEnd + "px; opacity: 20%;}"));
    eff.setTransitionType(new EaseInTransitionPhysics());
    eff.setEffectElement(this.getElement());
    eff.addEffectCompletedHandler(new EffectCompletedHandler() {
      @Override
      public void onEffectCompleted(EffectCompletedEvent event) {
        KeyboardNumericPopup.this.setVisible(false);
        KeyboardNumericPopup.this.setStep(Step.HIDDEN);
        KeyboardNumericPopup.this.hideOverlay();
        // eventBus.fireEvent(new KeyboardNumCloseEvent());
      }
    });
    eff.setDuration(0.5);
    eff.play();
  }

  public void openAnimated_setPopupPosition(int leftStart, int leftEnd, int topStart, int topEnd) {

    if (step != Step.HIDDEN) {
      return;
    }
    setStep(Step.MOVING);
    Logger.debug("KeyboardPopup: leftStart:" + leftStart + ", leftEnd:" + leftEnd + ", topStart:"
        + topStart + ", topEnd:" + topEnd);

    NMorphStyle eff = new NMorphStyle(new Rule("start{position: absolute; left: " + leftStart
        + "px; top: " + topStart + "px; opacity: 0%;}"), new Rule("end{position: absolute; left: "
        + leftEnd + "px; top: " + topEnd + "px; opacity: 100%;}"));
    eff.setTransitionType(new EaseOutTransitionPhysics());
    eff.setEffectElement(this.getElement());
    eff.addEffectCompletedHandler(new EffectCompletedHandler() {
      @Override
      public void onEffectCompleted(EffectCompletedEvent event) {
        KeyboardNumericPopup.this.setStep(Step.OPENED);
        KeyboardNumericPopup.this.showOverlay();
      }
    });
    eff.setDuration(0.5);
    eff.play();
  }

}
