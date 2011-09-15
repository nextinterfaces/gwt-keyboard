package com.ywebb.tablet.client.ui;

import org.adamtacy.client.ui.effects.core.NMorphStyle;
import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.Move;
import org.adamtacy.client.ui.effects.impl.css.Rule;
import org.adamtacy.client.ui.effects.transitionsphysics.LinearTransitionPhysics;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Element;

public class CUtil {

  public static NMorphStyle moveEffect(int fromX, int toX, Element element, final Command afterMoveCommand) {
    int top = CConstants.NAV_HIGHT; // getAbsoluteTop();
    return moveHorizEffect(fromX, toX, element, afterMoveCommand, 100, 100);
  }

  public static NMorphStyle moveHorizEffect(int fromX, int toX, Element element, final Command afterMoveCommand,
      int fromOpacity, int toOpacity) {
    return moveHorizEffect(fromX, toX, element, afterMoveCommand, fromOpacity, toOpacity, 0.3);
  }

  public static NMorphStyle moveHorizEffect(int fromX, int toX, Element element, final Command afterMoveCommand,
      int fromOpacity, int toOpacity, double duration) {
    // position: absolute
    Move eff = new Move(new Rule("start{position: relative; left:" + fromX + "px; opacity: " + fromOpacity + "%;}"),
        new Rule("end{position: relative; left: " + toX + "px; opacity: " + toOpacity + "%;}"));
    eff.setEffectElement(element);
    if (afterMoveCommand != null) {
      eff.addEffectCompletedHandler(new EffectCompletedHandler() {
        @Override
        public void onEffectCompleted(EffectCompletedEvent event) {
          afterMoveCommand.execute();
        }
      });
    }
    // eff.setTransitionType(new LinearTransitionPhysics());
    eff.setDuration(duration);
    return eff;
  }

  public static NMorphStyle moveHorizEffectAbs(int fromX, int toX, Element element, final Command afterMoveCommand,
      int fromOpacity, int toOpacity, double duration) {
    // position: absolute
    Move eff = new Move(new Rule("start{position: absolute; left:" + fromX + "px; top: 0px;opacity: " + fromOpacity
        + "%;}"), new Rule("end{position: absolute; left: " + toX + "px; top: 0px; opacity: " + toOpacity + "%;}"));
    eff.setEffectElement(element);
    if (afterMoveCommand != null) {
      eff.addEffectCompletedHandler(new EffectCompletedHandler() {
        @Override
        public void onEffectCompleted(EffectCompletedEvent event) {
          afterMoveCommand.execute();
        }
      });
    }
    eff.setTransitionType(new LinearTransitionPhysics());
    eff.setDuration(duration);
    return eff;
  }

  public static NMorphStyle opacityEffect(Element element, final Command afterCmd, int fromOpacity, int toOpacity) {

    Move eff = new Move(new Rule("start{opacity: " + fromOpacity + "%;}"),
        new Rule("end{opacity: " + toOpacity + "%;}"));
    eff.setEffectElement(element);

    if (afterCmd != null) {
      eff.addEffectCompletedHandler(new EffectCompletedHandler() {
        @Override
        public void onEffectCompleted(EffectCompletedEvent event) {
          afterCmd.execute();
        }
      });
    }

    // eff.setTransitionType(new LinearTransitionPhysics());
    eff.setDuration(0.3);
    return eff;
  }

  public static NMorphStyle moveVerticalEffect(int fromY, int toY, Element element, final Command afterMoveCommand) {
    Move eff = new Move(new Rule("start{position: absolute; left:" + 0 + "px; top: " + fromY + "px;}"), new Rule(
        "end{position: absolute; left: " + 0 + "px; top: " + toY + "px;}"));
    eff.setEffectElement(element);
    if (afterMoveCommand != null) {
      eff.addEffectCompletedHandler(new EffectCompletedHandler() {
        @Override
        public void onEffectCompleted(EffectCompletedEvent event) {
          afterMoveCommand.execute();
        }
      });
    }
    // eff.setTransitionType(new LinearTransitionPhysics());
    eff.setDuration(0.3);
    return eff;
  }

  public static NMorphStyle moveEffect(int fromX, int toX, Element element) {
    return moveEffect(fromX, toX, element, null);
  }

}
