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
package tv.dotstart.watari.common.service

/**
 * Exposes metadata which facilitate the selection and identification of service implementations.
 *
 * @author Johannes Donath
 * @date 15/07/2022
 * @since 0.1.0
 */
interface Service {

  /**
   * Defines a globally unique identifier via which this service may be consistently referenced.
   *
   * This value is provided in order to allow explicit selection of a given service via command
   * line switches, configuration files and other user facing elements.
   */
  val id: String

  /**
   * Provides a human readable name via which this service is referenced within application logs
   * or user interfaces.
   *
   * This information is primarily provided for debugging purposes.
   *
   * The default implementation of this property mimics the value provided by [id].
   */
  val name: String
    get() = this.id

  /**
   * Identifies whether this service implementation makes use of native libraries in order to
   * facilitate its functionality.
   *
   * This information is primarily provided for applications which wish to provide the user with a
   * more abstract choice (e.g. allow/disallow native network transports).
   *
   * The default implementation of this property returns `false` thus indicating that this
   * implementation does not make use of native components.
   */
  val native: Boolean
    get() = false

  /**
   * Identifies whether this service implementation is available within the current execution
   * environment.
   *
   * This property will be accessed prior to invoking any of the channel getters or factory
   * functions present within this interface. Note, however, that any metadata properties provided
   * by this interface may still be accessed when this implementation is marked unavailable for the
   * purposes of logging and/or displaying information to users within an interface.
   *
   * The default implementation of this property returns `true` thus indicating that this
   * implementation is available regardless of execution environment.
   */
  val available: Boolean
    get() = true

  /**
   * Identifies the relative priority with which this service is to be selected when multiple are
   * available within the current execution environment.
   *
   * On its own, a single priority value does not provide any meaningful information. It merely
   * represents the priority with which a service is to be selected relative to its peers. For the
   * purposes of this definition, higher value (e.g. closer to [Int.MAX_VALUE]) are more likely to
   * be selected whereas lower values (e.g. closer to [Int.MIN_VALUE]) are less likely to be
   * selected.
   *
   * This value should be an abstract representation of the viability of this service within its
   * respective execution environment. It should be defined statically (e.g. never change throughout
   * the lifetime of a transport instance).
   *
   * The default implementation of this property returns zero thus indicating no special preference
   * over other implementations within the Class-Path.
   */
  val priority: Int
    get() = 0
}
