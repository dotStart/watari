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
package tv.dotstart.watari.discovery.gateway.registry

import tv.dotstart.watari.common.service.registry.ServiceRegistry
import tv.dotstart.watari.discovery.gateway.GatewayDiscoveryService
import java.net.InetAddress
import java.net.NetworkInterface

/**
 * Provides a registry of installed gateway discovery services.
 *
 * @author Johannes Donath
 * @date 15/07/2022
 * @since 0.1.0
 */
interface GatewayDiscoveryServiceRegistry : ServiceRegistry<GatewayDiscoveryService> {

  /**
   * Performs a gateway discovery across the list of available discovery services in order of their
   * respective associated priority.
   *
   * This function will keep querying services until at least one of the available services returns
   * an acceptable value or the list is exhausted.
   */
  fun discover(): Pair<NetworkInterface, InetAddress>? =
    this.available
      .sortedByDescending(GatewayDiscoveryService::priority)
      .firstNotNullOf(GatewayDiscoveryService::discover)
}
