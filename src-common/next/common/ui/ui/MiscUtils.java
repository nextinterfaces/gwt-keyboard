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
package next.common.ui.ui;

import next.common.ui.ui.kbd.KeyboardResources;

import org.adamtacy.client.ui.effects.impl.Fade;
import org.adamtacy.client.ui.effects.impl.NShow;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A class for misc utilities.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
 */
public class MiscUtils {

  private final static KeyboardResources kbl = KeyboardResources.INSTANCE;

  /**
   * Returns up and down keyboard images
   */
  public static Image[] getImage(String key) {

    Image[] img = new Image[2];
    if ("q".equals(key)) {
      img[0] = new Image(kbl.Q());
      img[1] = new Image(kbl.Q_());

    } else if ("w".equals(key)) {
      img[0] = new Image(kbl.W());
      img[1] = new Image(kbl.W_());

    } else if ("e".equals(key)) {
      img[0] = new Image(kbl.E());
      img[1] = new Image(kbl.E_());

    } else if ("r".equals(key)) {
      img[0] = new Image(kbl.R());
      img[1] = new Image(kbl.R_());

    } else if ("t".equals(key)) {
      img[0] = new Image(kbl.T());
      img[1] = new Image(kbl.T_());

    } else if ("y".equals(key)) {
      img[0] = new Image(kbl.Y());
      img[1] = new Image(kbl.Y_());

    } else if ("u".equals(key)) {
      img[0] = new Image(kbl.U());
      img[1] = new Image(kbl.U_());

    } else if ("i".equals(key)) {
      img[0] = new Image(kbl.I());
      img[1] = new Image(kbl.I_());

    } else if ("o".equals(key)) {
      img[0] = new Image(kbl.O());
      img[1] = new Image(kbl.O_());

    } else if ("p".equals(key)) {
      img[0] = new Image(kbl.P());
      img[1] = new Image(kbl.P_());

    } else if ("a".equals(key)) {
      img[0] = new Image(kbl.A());
      img[1] = new Image(kbl.A_());

    } else if ("s".equals(key)) {
      img[0] = new Image(kbl.S());
      img[1] = new Image(kbl.S_());

    } else if ("d".equals(key)) {
      img[0] = new Image(kbl.D());
      img[1] = new Image(kbl.D_());

    } else if ("f".equals(key)) {
      img[0] = new Image(kbl.F());
      img[1] = new Image(kbl.F_());

    } else if ("g".equals(key)) {
      img[0] = new Image(kbl.G());
      img[1] = new Image(kbl.G_());

    } else if ("h".equals(key)) {
      img[0] = new Image(kbl.H());
      img[1] = new Image(kbl.H_());

    } else if ("j".equals(key)) {
      img[0] = new Image(kbl.J());
      img[1] = new Image(kbl.J_());

    } else if ("k".equals(key)) {
      img[0] = new Image(kbl.K());
      img[1] = new Image(kbl.K_());

    } else if ("l".equals(key)) {
      img[0] = new Image(kbl.L());
      img[1] = new Image(kbl.L_());

    } else if ("z".equals(key)) {
      img[0] = new Image(kbl.Z());
      img[1] = new Image(kbl.Z_());

    } else if ("x".equals(key)) {
      img[0] = new Image(kbl.X());
      img[1] = new Image(kbl.X_());

    } else if ("c".equals(key)) {
      img[0] = new Image(kbl.C());
      img[1] = new Image(kbl.C_());

    } else if ("v".equals(key)) {
      img[0] = new Image(kbl.V());
      img[1] = new Image(kbl.V_());

    } else if ("b".equals(key)) {
      img[0] = new Image(kbl.B());
      img[1] = new Image(kbl.B_());

    } else if ("n".equals(key)) {
      img[0] = new Image(kbl.N());
      img[1] = new Image(kbl.N_());

    } else if ("m".equals(key)) {
      img[0] = new Image(kbl.M());
      img[1] = new Image(kbl.M_());

    } else if ("1".equals(key)) {
      img[0] = new Image(kbl.n1());
      img[1] = new Image(kbl.n1_());

    } else if ("2".equals(key)) {
      img[0] = new Image(kbl.n2());
      img[1] = new Image(kbl.n2_());

    } else if ("3".equals(key)) {
      img[0] = new Image(kbl.n3());
      img[1] = new Image(kbl.n3_());

    } else if ("4".equals(key)) {
      img[0] = new Image(kbl.n4());
      img[1] = new Image(kbl.n4_());

    } else if ("5".equals(key)) {
      img[0] = new Image(kbl.n5());
      img[1] = new Image(kbl.n5_());

    } else if ("6".equals(key)) {
      img[0] = new Image(kbl.n6());
      img[1] = new Image(kbl.n6_());

    } else if ("7".equals(key)) {
      img[0] = new Image(kbl.n7());
      img[1] = new Image(kbl.n7_());

    } else if ("8".equals(key)) {
      img[0] = new Image(kbl.n8());
      img[1] = new Image(kbl.n8_());

    } else if ("9".equals(key)) {
      img[0] = new Image(kbl.n9());
      img[1] = new Image(kbl.n9_());

    } else if ("0".equals(key)) {
      img[0] = new Image(kbl.n0());
      img[1] = new Image(kbl.n0_());

    } else if ("AT".equals(key)) {
      img[0] = new Image(kbl.at());
      img[1] = new Image(kbl.atDown());

    } else if ("SH".equals(key)) {
      img[0] = new Image(kbl.shift());
      img[1] = new Image(kbl.shiftDown());

    } else if ("BS".equals(key)) {
      img[0] = new Image(kbl.backspace());
      img[1] = new Image(kbl.backspaceDown());

    } else if ("EN".equals(key)) {
      img[0] = new Image(kbl.enter());
      img[1] = new Image(kbl.enterDown());

    } else if ("DO".equals(key)) {
      img[0] = new Image(kbl.done());
      img[1] = new Image(kbl.doneDown());

    } else if ("SP".equals(key)) {
      img[0] = new Image(kbl.space());
      img[1] = new Image(kbl.spaceDown());

    } else if ("DASH".equals(key)) {
      img[0] = new Image(kbl.dash());
      img[1] = new Image(kbl.dashDown());

    }

    return img;

  }

  public static void transitionWidgets(FlexTable flexTable, int row, int column, Widget newWidget) {

    Widget prevWidg = null;

    try {
      prevWidg = flexTable.getWidget(row, column);
    } catch (IndexOutOfBoundsException e) {
      // normal behavior. FlexTabel with no rows and columns
    }
    if (prevWidg != null) {
      Fade eff = new Fade(prevWidg.getElement());
      eff.setDuration(0.5);
      eff.play();
    }

    newWidget.getElement().getStyle().setOpacity(0);
    flexTable.setWidget(row, column, newWidget);
    NShow eff = new NShow(newWidget.getElement());
    eff.setDuration(0.5);
    eff.play();
  }

  public static void transitionWidgets(SimplePanel panel, Widget newWidget) {

    Widget prevWidg = panel.getWidget();

    if (prevWidg != null) {
      Fade eff = new Fade(prevWidg.getElement());
      eff.setDuration(0.5);
      eff.play();
    }

    newWidget.getElement().getStyle().setOpacity(0);
    panel.setWidget(newWidget);
    NShow eff = new NShow(newWidget.getElement());
    eff.setDuration(0.5);
    eff.play();
  }

}
