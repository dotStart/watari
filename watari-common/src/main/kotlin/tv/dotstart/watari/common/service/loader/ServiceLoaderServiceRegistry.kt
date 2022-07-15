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
package tv.dotstart.watari.common.service.loader

import tv.dotstart.watari.common.lazy.revertibleLazy
import tv.dotstart.watari.common.service.Service
import java.util.*
import kotlin.reflect.KClass

/**
 * Provides a service registry implementation which relies on the service loader infrastructure
 * provided by the JDK in order to discover available implementations within the application
 * Class-Path.
 *
 * @author Johannes Donath
 * @date 15/07/2022
 * @since 0.1.0
 */
open class ServiceLoaderServiceRegistry<out I : Service> protected constructor(
  private val loader: ServiceLoader<I>
) : ServiceRegistry<I> {

  /**
   * Creates a transport loader for the thread's current context class loader.
   */
  constructor(type: KClass<I>) : this(ServiceLoader.load(type.java))

  /**
   * Creates a transport loader for the selected class loader.
   */
  constructor(type: KClass<I>, classLoader: ClassLoader) : this(
    ServiceLoader.load(
      type.java,
      classLoader
    )
  )

  /**
   * Creates a transport loader for the selected module layer.
   */
  constructor(type: KClass<I>, layer: ModuleLayer) : this(
    ServiceLoader.load(layer, type.java)
  )

  private var _installed = revertibleLazy(this, initializer = this.loader::toList)
  private var _available = revertibleLazy(this) {
    this._installed.value
      .filter(Service::available)
  }

  override final val installed by _installed
  override val available by _available

  override fun refresh() {
    this.loader.reload()

    synchronized(this, this::reset)
  }

  protected open fun reset() {
    this._installed.reset()
    this._available.reset()
  }
}
