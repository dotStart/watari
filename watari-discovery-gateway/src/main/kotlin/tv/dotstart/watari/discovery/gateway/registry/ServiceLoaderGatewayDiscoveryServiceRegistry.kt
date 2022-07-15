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

import tv.dotstart.watari.common.service.registry.ServiceLoaderServiceRegistry
import tv.dotstart.watari.discovery.gateway.GatewayDiscoveryService
import java.util.*

/**
 * Provides a service loader architecture based registry for gateway discovery services which
 * automatically locates installed implementations within the application Class-Path.
 *
 * @author Johannes Donath
 * @date 15/07/2022
 * @since 0.1.0
 */
class ServiceLoaderGatewayDiscoveryServiceRegistry private constructor(loader: ServiceLoader<GatewayDiscoveryService>) :
  ServiceLoaderServiceRegistry<GatewayDiscoveryService>(loader), GatewayDiscoveryServiceRegistry {

  /**
   * Creates a registry for the thread's current context class loader.
   */
  constructor() : this(ServiceLoader.load(GatewayDiscoveryService::class.java))

  /**
   * Creates a registry for the selected class loader.
   */
  constructor(classLoader: ClassLoader) : this(
    ServiceLoader.load(
      GatewayDiscoveryService::class.java,
      classLoader
    )
  )

  /**
   * Creates a registry for the selected module layer.
   */
  constructor(layer: ModuleLayer) : this(
    ServiceLoader.load(layer, GatewayDiscoveryService::class.java)
  )
}
