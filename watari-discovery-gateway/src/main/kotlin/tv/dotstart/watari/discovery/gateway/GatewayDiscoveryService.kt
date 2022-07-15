/*
 * Copyright 2022 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tv.dotstart.watari.discovery.gateway

import tv.dotstart.watari.common.service.Service
import java.net.InetAddress
import java.net.NetworkInterface

/**
 * Facilitates the discovery of gateway devices available to the executing machine.
 *
 * @author Johannes Donath
 * @date 14/07/2022
 * @since 0.1.0
 */
interface GatewayDiscoveryService : Service {

  /**
   * Attempts to discover the address of the default gateway as well as the interface from which it
   * is reachable.
   *
   * Note that this function does not guarantee the reachability of the gateway but merely provides
   * information on the perceived system configuration. Some providers may provide more accurate
   * information than others.
   *
   * When the provider is unable to resolve a valid configuration, null is returned instead. Callers
   * should select the next available provider in this case.
   */
  fun discover(): Pair<NetworkInterface, InetAddress>?
}
