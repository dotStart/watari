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
package tv.dotstart.watari.transport.registry

import tv.dotstart.watari.common.lazy.revertibleLazy
import tv.dotstart.watari.common.service.registry.ServiceLoaderServiceRegistry
import tv.dotstart.watari.transport.Transport
import java.util.*

/**
 * Discovers transports through the [ServiceLoader] architecture.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
class ServiceTransportLoader private constructor(loader: ServiceLoader<Transport>) :
  ServiceLoaderServiceRegistry<Transport>(loader), TransportLoader {

  /**
   * Creates a transport loader for the thread's current context class loader.
   */
  constructor() : this(ServiceLoader.load(Transport::class.java))

  /**
   * Creates a transport loader for the selected class loader.
   */
  constructor(classLoader: ClassLoader) : this(
    ServiceLoader.load(
      Transport::class.java,
      classLoader
    )
  )

  /**
   * Creates a transport loader for the selected module layer.
   */
  constructor(layer: ModuleLayer) : this(
    ServiceLoader.load(layer, Transport::class.java)
  )

  private var _optimal = revertibleLazy<Transport?>(this) {
    this.available
      .maxBy(Transport::priority)
  }

  override val optimal by _optimal

  override fun reset() {
    this._optimal.reset()
  }
}
