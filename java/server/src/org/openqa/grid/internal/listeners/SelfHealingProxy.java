// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.openqa.grid.internal.listeners;

import org.openqa.grid.common.exception.RemoteException;

import java.util.List;

/**
 * Defines of how the proxy tries to mitigate system errors like network issues etc. When a proxy
 * implements this interface, the polling will start when the proxy is fully registered to the
 * GridRegistry, ie after the Registration.beforeRegistration() is done.
 */
public interface SelfHealingProxy {

  /**
   * start/restart the polling for the remote proxy. A typical poll will try to contact the remote
   * proxy to see if it's still accessible, but it can have more logic in it, like checking the
   * resource usage ( RAM etc) on the remote.
   */
  void startPolling();

  /**
   * put the polling on hold.
   */
  void stopPolling();

  /**
   * Allow to record when something important about the remote state is detected.
   *
   * @param event RemoteException event to be called when something happens
   */
  void addNewEvent(RemoteException event);

  // TODO freynaud pass the list as a param ?

  /**
   * Allow to process the list of all the events that were detected on this Remote so far. A typical
   * implementation of this method will be to put the proxy on hold if the network connection is
   * bad, or to restart the remote if the resources used are too important
   *
   * @param events list of RemoteExceptions occurred
   * @param lastInserted last event that occurred
   */
  void onEvent(List<RemoteException> events, RemoteException lastInserted);

}
