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
package tv.dotstart.watari.common.lazy

/**
 * Provides a revertible lazy implementation which relies on synchronization in order to ensure
 * single initialization semantics.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
private class SynchronizedRevertibleLazy<out T>(

  /**
   * Defines an initializer function which provides the value with which this object is populated
   * when it has not yet been accessed or when [reset] is invoked.
   */
  private var initializer: () -> T,

  /**
   * Defines the object which shall act as a lock to safe keep the initialization procedure.
   *
   * When `null` is passed, the object itself will act as a lock.
   */
  lock: Any?
) : RevertibleLazy<T> {

  /**
   * Stores a lock which is used to prevent duplicate initialization of the object when multiple
   * threads access the object while uninitialized.
   */
  private val lock: Any = lock ?: this

  /**
   * Stores the currently present value for this lazy.
   */
  @Volatile
  private var _value: Any? = UninitializedValue

  @Suppress("UNCHECKED_CAST")
  override val value: T
    get() {
      // attempt to access the value without protection first as lazily initialized values are
      // typically reset rarely - this provides a slight performance benefit over eagerly locking
      // on highly contested instances
      val unprotectedValue = this._value
      if (unprotectedValue != UninitializedValue) {
        return unprotectedValue as T
      }

      // if the value has yet to be initialized, we'll have to actually enter the lock to determine
      // the final state in case that initialization occurs on multiple threads at once
      synchronized(this.lock) {
        // perform a final check on the value within protection as another thread may have completed
        // the initialization process while we were waiting for the lock to free up
        val protectedValue = this._value
        if (protectedValue != null) {
          return protectedValue as T
        }

        // if the value is still not initialized, we are the first thread to perform initialization
        // thus allowing us to call the initializer function
        val initializedValue = this.initializer()
        this._value = initializedValue
        return initializedValue
      }
    }

  override fun isInitialized() = this._value != UninitializedValue

  override fun reset() {
    synchronized(this.lock) {
      this._value = null
    }
  }

  /**
   * Provides a marker object which is used in order to identify whether the object has been
   * initialized or not.
   *
   * This value is necessary as the initialized value may potentially be `null` thus making a
   * classic detection impossible.
   */
  private object UninitializedValue
}

/**
 * Creates a new revertible lazy object.
 */
fun <T> revertibleLazy(lock: Any? = null, initializer: () -> T): RevertibleLazy<T> =
  SynchronizedRevertibleLazy(initializer, lock)
