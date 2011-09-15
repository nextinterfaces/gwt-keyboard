/*
 * Copyright 2008-2009 Adam Tacy <adam.tacy AT gmail.com>
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
package org.adamtacy.client.ui.effects;

/**
 * Marker interface for a sequential composite effect.
 * 
 */
public interface SequentialCompositeEffect{


//  private int delayBetweenEffect = 0;


//  public void initCompositeEffect() {
//    for (int loop = 0; loop < internalEffects.size() - 1; loop++) {
//      NEffect firstEffect = (internalEffects.get(loop));
//      final NEffect secondEffect = (internalEffects.get(loop + 1));
//      firstEffect.addEffectCompletedHandler(new EffectCompletedHandler(){
//        public void onEffectCompleted(EffectCompletedEvent event) {
//          if (delayBetweenEffect > 0) {
//            Timer t = new Timer() {
//              @Override
//              public void run() {
//                beginNextEffect(secondEffect);
//              }
//            };
//            t.schedule(delayBetweenEffect);
//          } else {
//            beginNextEffect(secondEffect);
//          }
//        }
//      });
//    }
//    (internalEffects.get(internalEffects.size() - 1)).addEffectCompletedHandler(new EffectCompletedHandler(){
//      public void onEffectCompleted(EffectCompletedEvent event) {
//        finishCompositeEffect();        
//      }
//    });
//    this.setEffectLength();
//    initialized = true;
//  }


  void setEffectBetweenDelay(double seconds);
//  {
//    delayBetweenEffect = new Double(seconds * 1000).intValue();
//  }

}