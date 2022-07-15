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

import tv.dotstart.watari.common.service.registry.ServiceRegistry
import tv.dotstart.watari.transport.Transport

/**
 * Handles the discovery of available [Transport] implementations within the current execution
 * environment.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 * @see ServiceTransportLoader for a default implementation.
 */
interface TransportLoader : ServiceRegistry<Transport> {

  companion object {

    /**
     * Provides a default class loader which relies on [ServiceTransportLoader].
     */
    val DEFAULT by lazy { ServiceTransportLoader(ClassLoader.getSystemClassLoader()) }
  }

  /**
   * Retrieves the most optimal transport implementation which is available within the current
   * execution environment.
   */
  val optimal: Transport?
    get() = this.available
      .maxBy(Transport::priority)
}
