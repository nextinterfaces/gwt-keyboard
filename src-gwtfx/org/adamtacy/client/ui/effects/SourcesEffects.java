/*
 * Copyright 2008 Adam Tacy <adam.tacy AT gmail.com>
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
 * A widget that implements this interface sources the events defined by the
 * {@link NEffect} interface.
 * @deprecated covers the same as HasEffects which is more appropriate (see issue #73)
 */
public interface SourcesEffects { 
  /**
   * Adds a listener interface to receive click events.
   * 
   * @param listener the listener interface to add
   */
  void addEffect(NEffect theEffect);

  /**
   * Removes a previously added listener interface.
   * 
   * @param listener the listener interface to remove
   */
  void removeEffect(NEffect theEffect);
}
