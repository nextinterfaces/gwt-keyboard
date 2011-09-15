package com.ywebb.component.client.ui.kbd;

import org.adamtacy.client.ui.effects.core.NMorphStyle;
import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.css.Rule;
import org.adamtacy.client.ui.effects.transitionsphysics.EaseInTransitionPhysics;
import org.adamtacy.client.ui.effects.transitionsphysics.EaseOutTransitionPhysics;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.ywebb.component.client.ui.ElementIds;
import com.ywebb.component.client.ui.Logger;
import com.ywebb.component.client.ui.MiscUtils;

/**
 * This is the main QWERTY keyboard widget.
 * <p>
 * 
 * @author Atanas Roussev
 */
public class KeyboardPopup extends PopupPanel {

  private KeyboardTextBox textBox;
  private static String[] KEYS_QWERTY = { "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "#2",
      "7", "8", "9", "", "a", "s", "d", "f", "g", "h", "j", "k", "l", "BS", "4", "5", "6", "",
      "SH", "z", "x", "c", "v", "b", "n", "m", "#0", "EN", "1", "2", "3", "", " ", " ", "SP", "#1",
      "DO", "AT", "0", "DASH" };

  public static enum Step {
    HIDDEN, MOVING, OPENED
  }

  private Step step = Step.HIDDEN;

  public KeyboardPopup() {

    setStyleName("b_keyboard");
    getElement().setId(ElementIds.KEYBOARD);

    FlowPanel flowPanel = new FlowPanel();
    add(flowPanel);
    buildKeyboard(flowPanel);

    // Window.addResizeHandler(new ResizeHandler() {
    // @Override
    // public void onResize(ResizeEvent event) {
    // if (KeyboardPopup.this.isVisible()) {
    // KeyboardPopup.this.setPopupPosition((int) ((Window.getClientWidth() -
    // KeyboardPopup.this
    // .getOffsetWidth()) / 2.0), -KeyboardPopup.this.getOffsetHeight() - 2);
    // }
    // }
    // });
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
    
    if (step != Step.OPENED) {
      return;
    }
    
    try {

      // e.g. parse previous 'margin-left:-300px'; and animate left close based
      // on this position
      String marginLeft = getElement().getStyle().getProperty("marginLeft");

      marginLeft = marginLeft.substring(0, marginLeft.length() - 2);
      int leftStart = -Math.abs(Integer.valueOf(marginLeft));
      int leftEnd = leftStart;
      int topStart = getAbsoluteTop();
      int topEnd = Window.getClientHeight();

      // int leftStart = (int) (this.getOffsetWidth() / 2.0);
      // int leftEnd = leftStart;
      // int topStart = Window.getClientHeight() - getOffsetHeight();
      // int topEnd = Window.getClientHeight();

      closeAnimated(leftStart, leftEnd, topStart, topEnd);

      //FIXME evaluate this approach as the excpetion propagates to JavaScript as NumberFormatException 
    } catch (StringIndexOutOfBoundsException e) {
      e.printStackTrace();// probably normal behaviour
    }
  }

  public void closeAnimated(int leftStart, int leftEnd, int topStart, int topEnd) {

    if (step != Step.OPENED) {
      return;
    }
    // 1st try using "position: relative"
    // int top = this.getOffsetHeight();
    // int left = (int) ((Window.getClientWidth() - this.getOffsetWidth()) /
    // 2.0);
    // int offsetForm = CTX.getUiSingleton().getFormPopup().getOffsetHeight();
    // final NMorphStyle eff = new NMorphStyle(new Rule("start{left: " + left +
    // "px; top: "
    // + (-top - offsetForm) + "px; opacity: 100%;}"), new Rule("end{left: " +
    // left + "px; top: "
    // + (0 - offsetForm) + "px; opacity: 0%;}"));
    //
    // eff.setEffectElement(this.getElement());
    // eff.addEffectCompletedHandler(new EffectCompletedHandler() {
    //
    // @Override
    // public void onEffectCompleted(EffectCompletedEvent event) {
    // KeyboardPopup.this.setVisible(false);
    // KeyboardPopup.this.setStep(Step.HIDDEN);
    // }
    // });
    // eff.setDuration(0.5);
    // eff.play();

    NMorphStyle eff = new NMorphStyle(new Rule("start{position: absolute; left: 50%; margin-left: "
        + leftStart + "px; top: " + topStart + "px; opacity: 100%;}"), new Rule(
        "end{position: absolute; left: 50%; margin-left: " + leftEnd + "px; top: " + topEnd
            + "px; opacity: 20%;}"));
    eff.setTransitionType(new EaseInTransitionPhysics());
    eff.setEffectElement(this.getElement());
    eff.addEffectCompletedHandler(new EffectCompletedHandler() {
      @Override
      public void onEffectCompleted(EffectCompletedEvent event) {
        KeyboardPopup.this.setVisible(false);
        KeyboardPopup.this.setStep(Step.HIDDEN);
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

    NMorphStyle eff = new NMorphStyle(new Rule(
        "start{position: absolute; left: 50%; margin-left: -" + leftStart + "px; top: " + topStart
            + "px; opacity: 0%;}"), new Rule("end{position: absolute; left: 50%; margin-left: -"
        + leftEnd + "px; top: " + topEnd + "px; opacity: 100%;}"));
    eff.setTransitionType(new EaseOutTransitionPhysics());
    eff.setEffectElement(this.getElement());
    eff.addEffectCompletedHandler(new EffectCompletedHandler() {
      @Override
      public void onEffectCompleted(EffectCompletedEvent event) {
        KeyboardPopup.this.setStep(Step.OPENED);
      }
    });
    eff.setDuration(0.5);
    eff.play();
  }

  // public void openAnimated_setPopupPosition(int offsetWidth, int
  // offsetHeight) {
  //
  // if (step != Step.HIDDEN) {
  // return;
  // }
  // setStep(Step.MOVING);
  // Logger.debug("KeyboardPopup: offsetWidth:" + offsetWidth +
  // ", offsetHeight:" + offsetHeight);
  //
  // // int targetLeft = (int) ((Window.getClientWidth() - offsetWidth) / 2.0);
  //
  // // 1st try
  // // Fade theFade = new Fade(this.getElement());
  // // // Fade the component
  // // theFade.play();
  //
  // // 2nd try
  // // NMorphStyle flyEffect = new NMorphStyle(new Rule("start{left: " +
  // // targetLeft + "px; top: " + 0
  // // + "px;}"), new Rule("end{left: " + targetLeft + "px; top: " +
  // // (-offsetHeight) + "px;}"));
  // // NMorphStyle flyEffect = new NMorphStyle(new Rule("slideStart{left: " +
  // // targetLeft + "px; top: "
  // // + 0 + "px;}"), new Rule("slideEnd{left: " + targetLeft + "px; top: " +
  // // (-offsetHeight)
  // // + "px;}"));
  //
  // // // 3rd try
  // // final NMorphStyle eff = new NMorphStyle(new Rule("start{left: " +
  // // targetLeft + "px; top: " + 0
  // // + "px; opacity: 0%;}"), new Rule("end{left: " + targetLeft + "px; top: "
  // // + (-offsetHeight -
  // CTX.getUiSingleton().getFormPopup().getOffsetHeight())
  // // + "px; opacity: 100%;  }"));
  // //
  // // eff.setEffectElement(this.getElement());
  // // eff.addEffectCompletedHandler(new EffectCompletedHandler() {
  // //
  // // @Override
  // // public void onEffectCompleted(EffectCompletedEvent event) {
  // // KeyboardPopup.this.setStep(Step.OPENED);
  // // }
  // // });
  // // eff.setDuration(0.5);
  // // eff.play();
  //
  // NMorphStyle eff = new NMorphStyle(new Rule(
  // "start{position: absolute; left: 50%; margin-left: -" + (offsetWidth / 2.0)
  // + "px; top: "
  // + (Window.getClientHeight()) + "px; opacity: 0%;}"), new Rule(
  // "end{position: absolute; left: 50%; margin-left: -" + (offsetWidth / 2.0) +
  // "px; top: "
  // + (Window.getClientHeight() - offsetHeight) + "px; opacity: 100%;}"));
  // // eff.setTransitionType(new EaseOutTransitionPhysics());
  // eff.setEffectElement(this.getElement());
  // eff.addEffectCompletedHandler(new EffectCompletedHandler() {
  // @Override
  // public void onEffectCompleted(EffectCompletedEvent event) {
  // KeyboardPopup.this.setStep(Step.OPENED);
  // }
  // });
  // eff.setDuration(0.5);
  // eff.play();
  // }

}
