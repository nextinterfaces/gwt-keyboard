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

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * This class holds all keyboard images.
 * <p>
 * 
 * @author Atanas Roussev ( http://nextinterfaces.com )
 */
public interface KeyboardResources extends ClientBundle {

  public static final KeyboardResources INSTANCE = GWT.create(KeyboardResources.class);

  //  
  // @NotStrict
  // @Source("resources/main.css")
  // public CssResource cssMain();

  @Source("resources/t_box_clear.png")
  public ImageResource t_box_clear();
  
  @Source("resources/0.png")
  public ImageResource n0();

  @Source("resources/1.png")
  public ImageResource n1();

  @Source("resources/2.png")
  public ImageResource n2();

  @Source("resources/3.png")
  public ImageResource n3();

  @Source("resources/4.png")
  public ImageResource n4();

  @Source("resources/5.png")
  public ImageResource n5();

  @Source("resources/6.png")
  public ImageResource n6();

  @Source("resources/7.png")
  public ImageResource n7();

  @Source("resources/8.png")
  public ImageResource n8();

  @Source("resources/9.png")
  public ImageResource n9();

  @Source("resources/q.png")
  public ImageResource Q();

  @Source("resources/w.png")
  public ImageResource W();

  @Source("resources/e.png")
  public ImageResource E();

  @Source("resources/r.png")
  public ImageResource R();

  @Source("resources/t.png")
  public ImageResource T();

  @Source("resources/y.png")
  public ImageResource Y();

  @Source("resources/u.png")
  public ImageResource U();

  @Source("resources/i.png")
  public ImageResource I();

  @Source("resources/o.png")
  public ImageResource O();

  @Source("resources/p.png")
  public ImageResource P();

  @Source("resources/a.png")
  public ImageResource A();

  @Source("resources/s.png")
  public ImageResource S();

  @Source("resources/d.png")
  public ImageResource D();

  @Source("resources/f.png")
  public ImageResource F();

  @Source("resources/g.png")
  public ImageResource G();

  @Source("resources/h.png")
  public ImageResource H();

  @Source("resources/j.png")
  public ImageResource J();

  @Source("resources/k.png")
  public ImageResource K();

  @Source("resources/l.png")
  public ImageResource L();

  @Source("resources/z.png")
  public ImageResource Z();

  @Source("resources/x.png")
  public ImageResource X();

  @Source("resources/c.png")
  public ImageResource C();

  @Source("resources/v.png")
  public ImageResource V();

  @Source("resources/b.png")
  public ImageResource B();

  @Source("resources/n.png")
  public ImageResource N();

  @Source("resources/m.png")
  public ImageResource M();

  @Source("resources/0_.png")
  public ImageResource n0_();

  @Source("resources/1_.png")
  public ImageResource n1_();

  @Source("resources/2_.png")
  public ImageResource n2_();

  @Source("resources/3_.png")
  public ImageResource n3_();

  @Source("resources/4_.png")
  public ImageResource n4_();

  @Source("resources/5_.png")
  public ImageResource n5_();

  @Source("resources/6_.png")
  public ImageResource n6_();

  @Source("resources/7_.png")
  public ImageResource n7_();

  @Source("resources/8_.png")
  public ImageResource n8_();

  @Source("resources/9_.png")
  public ImageResource n9_();

  @Source("resources/q_.png")
  public ImageResource Q_();

  @Source("resources/w_.png")
  public ImageResource W_();

  @Source("resources/e_.png")
  public ImageResource E_();

  @Source("resources/r_.png")
  public ImageResource R_();

  @Source("resources/t_.png")
  public ImageResource T_();

  @Source("resources/y_.png")
  public ImageResource Y_();

  @Source("resources/u_.png")
  public ImageResource U_();

  @Source("resources/i_.png")
  public ImageResource I_();

  @Source("resources/o_.png")
  public ImageResource O_();

  @Source("resources/p_.png")
  public ImageResource P_();

  @Source("resources/a_.png")
  public ImageResource A_();

  @Source("resources/s_.png")
  public ImageResource S_();

  @Source("resources/d_.png")
  public ImageResource D_();

  @Source("resources/f_.png")
  public ImageResource F_();

  @Source("resources/g_.png")
  public ImageResource G_();

  @Source("resources/h_.png")
  public ImageResource H_();

  @Source("resources/j_.png")
  public ImageResource J_();

  @Source("resources/k_.png")
  public ImageResource K_();

  @Source("resources/l_.png")
  public ImageResource L_();

  @Source("resources/z_.png")
  public ImageResource Z_();

  @Source("resources/x_.png")
  public ImageResource X_();

  @Source("resources/c_.png")
  public ImageResource C_();

  @Source("resources/v_.png")
  public ImageResource V_();

  @Source("resources/b_.png")
  public ImageResource B_();

  @Source("resources/n_.png")
  public ImageResource N_();

  @Source("resources/m_.png")
  public ImageResource M_();

  @Source("resources/backspace.png")
  public ImageResource backspace();

  @Source("resources/backspace_.png")
  public ImageResource backspaceDown();

  @Source("resources/shift.png")
  public ImageResource shift();

  @Source("resources/shift_.png")
  public ImageResource shiftDown();

  @Source("resources/at.png")
  public ImageResource at();

  @Source("resources/at_.png")
  public ImageResource atDown();

  @Source("resources/enter.png")
  public ImageResource enter();

  @Source("resources/enter_.png")
  public ImageResource enterDown();

  @Source("resources/done.png")
  public ImageResource done();

  @Source("resources/done_.png")
  public ImageResource doneDown();

  @Source("resources/space.png")
  public ImageResource space();

  @Source("resources/space_.png")
  public ImageResource spaceDown();

  @Source("resources/dash.png")
  public ImageResource dash();

  @Source("resources/dash_.png")
  public ImageResource dashDown();

}
