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

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.impl.NShow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * A text box being binded to KeyboardPopup. On click it opens the keyboard.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
 */
public class KeyboardTextBox extends Grid {

  private final TextBoxBase textBox;
  private final Image imgClear;
  private boolean isSelected = false;

  private KeyboardResources RES = KeyboardResources.INSTANCE;

  public KeyboardTextBox() {
    this(false);
  }

  public KeyboardTextBox(boolean isPasswordBox) {
    super(1, 3);

    setStyleName("b_textBox");

    if (isPasswordBox) {
      textBox = new PasswordTextBox();

    } else {
      textBox = new TextBox();
    }
    textBox.setStyleName("text");

    imgClear = new Image(RES.t_box_clear());
    imgClear.getElement().getStyle().setOpacity(0);
    imgClear.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        textBox.setText("");
      }
    });
    getCellFormatter().setStyleName(0, 0, "left");
    getCellFormatter().setStyleName(0, 1, "middle");
    getCellFormatter().setStyleName(0, 2, "right");
    getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
    getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);

    setWidget(0, 0, new HTML("&nbsp;"));
    setWidget(0, 1, textBox);
    setWidget(0, 2, imgClear);
  }

  public void setSelected(boolean selected) {
    if (selected) {
      if (!isSelected) {
        isSelected = true;
        addStyleName("b_textBoxSelected");
        NShow eff = new NShow(imgClear.getElement());
        eff.setDuration(.5);
        eff.addEffectCompletedHandler(new EffectCompletedHandler() {
          @Override
          public void onEffectCompleted(EffectCompletedEvent event) {
            if (!isSelected) {
              imgClear.getElement().getStyle().setOpacity(0);
            }
          }
        });
        eff.play();
      }

    } else {
      isSelected = false;
      removeStyleName("b_textBoxSelected");
      imgClear.getElement().getStyle().setOpacity(0);

    }
  }

  @Override
  public void setWidth(String width) {
    textBox.setWidth(width);
  }

  public String getText() {
    return textBox.getText();
  }

  public void setText(String text) {
    textBox.setText(text);
  }
}
