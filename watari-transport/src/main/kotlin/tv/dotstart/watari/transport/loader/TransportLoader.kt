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
package tv.dotstart.watari.transport.loader

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
interface TransportLoader {

  companion object {

    /**
     * Provides a default class loader which relies on [ServiceTransportLoader].
     */
    val DEFAULT by lazy { ServiceTransportLoader(ClassLoader.getSystemClassLoader()) }
  }

  /**
   * Retrieves a listing of transports which are installed within the current execution environment
   * but may not necessarily be available for selection.
   */
  val installed: List<Transport>

  /**
   * Retrieves a listing of transports which are installed and available to the current execution
   * environment.
   *
   * The default implementation of this property filters the return value of [installed] to exclude
   * any transports marked unavailable.
   */
  val available: List<Transport>
    get() = this.installed
      .filter(Transport::available)

  /**
   * Retrieves the most optimal transport implementation which is available within the current
   * execution environment.
   */
  val optimal: Transport?
    get() = this.available
      .maxBy(Transport::priority)

  /**
   * Refreshes the cached values within this transport loader.
   *
   * This function is optional and may not necessarily have any effect depending on the selected
   * transport loader implementation.
   *
   * The default implementation of this function acts as a NOOP.
   */
  fun refresh() = Unit
}
