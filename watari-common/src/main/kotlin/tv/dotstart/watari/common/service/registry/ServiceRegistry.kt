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
package tv.dotstart.watari.common.service.registry

import tv.dotstart.watari.common.service.Service

/**
 * Handles the discovery of installed and available [Service] implementations within the current
 * execution environment.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 * @see ServiceLoaderServiceRegistry for a default implementation.
 */
interface ServiceRegistry<out I : Service> {

  /**
   * Retrieves a listing of services which are installed within the current execution environment
   * but may not necessarily be available for selection.
   */
  val installed: List<I>

  /**
   * Retrieves a listing of services which are installed and available to the current execution
   * environment.
   *
   * The default implementation of this property filters the return value of [installed] to exclude
   * any services marked unavailable.
   */
  val available: List<I>
    get() = this.installed
      .filter(Service::available)

  /**
   * Retrieves a specific service based on its unique identifier.
   *
   * Please note that the returned service implementation may not necessarily be marked
   * available. It is the caller's responsibility to ensure the desired implementation is available
   * before interacting with its functions.
   *
   * When no implementation with the given identifier is present within the list of [installed]
   * implementations, `null` is returned instead.
   */
  operator fun get(id: String): I? =
    this.installed.find { it.id == id }

  /**
   * Refreshes the cached values within this service registry.
   *
   * This function is optional and may not necessarily have any effect depending on the selected
   * transport loader implementation.
   *
   * The default implementation of this function acts as a NOOP.
   */
  fun refresh() = Unit
}
