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
package tv.dotstart.watari.transport

import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.ServerSocketChannel
import io.netty.channel.socket.SocketChannel
import io.netty.channel.unix.DomainSocketChannel
import io.netty.channel.unix.ServerDomainSocketChannel
import java.util.concurrent.ThreadFactory
import kotlin.reflect.KClass

/**
 * Provides a netty network transport.
 *
 * Transports effectively encapsulate a certain API for the purposes of handling the reading,
 * writing and accepting of network connections within a given execution environment.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 * @since 0.1.0
 */
interface Transport {

  /**
   * Defines a globally unique identifier via which this transport may be consistently referenced.
   *
   * This value is provided in order to allow explicit selection of a given transport via command
   * line switches, configuration files and other user facing elements.
   */
  val id: String

  /**
   * Provides a human readable name via which this transport is referenced within application logs
   * or user interfaces.
   *
   * This information is primarily provided for debugging purposes.
   *
   * The default implementation of this property mimics the value provided by [id].
   */
  val name: String
    get() = this.id

  /**
   * Identifies whether this transport implementation makes use of native libraries in order to
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
   * Identifies whether this transport implementation is available within the current execution
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
   * Identifies the relative priority with which this transport is to be selected when multiple are
   * available within the current execution environment.
   *
   * On its own, a single priority value does not provide any meaningful information. It merely
   * represents the priority with which a transport is to be selected relative to its peers. For the
   * purposes of this definition, higher value (e.g. closer to [Int.MAX_VALUE]) are more likely to
   * be selected whereas lower values (e.g. closer to [Int.MIN_VALUE]) are less likely to be
   * selected.
   *
   * This value should be an abstract representation of the performance of this transport within its
   * respective execution environment. It should be defined statically (e.g. never change throughout
   * the lifetime of a transport instance).
   *
   * The default implementation of this property returns zero thus indicating no special preference
   * over other implementations within the Class-Path.
   */
  val priority: Int
    get() = 0

  /**
   * Identifies the channel implementation which facilitates TCP communication for this transport.
   */
  val socketChannelType: KClass<out SocketChannel>

  /**
   * Identifies the channel implementation which facilitates server-side TCP communication for this
   * transport.
   */
  val serverSocketChannelType: KClass<out ServerSocketChannel>

  /**
   * Identifies the channel implementation which facilitates UDP communication for this transport.
   */
  val datagramChannelType: KClass<out DatagramChannel>

  /**
   * Identifies the channel implementation which facilitates UNIX domain socket communication for
   * this transport.
   *
   * If this transport does not support communication via domain sockets, `null` is returned
   * instead.
   *
   * The default implementation of this property returns null thus indicating no support for UNIX
   * domain sockets.
   */
  val domainSocketChannelType: KClass<out DomainSocketChannel>?
    get() = null

  /**
   * Identifies the channel implementation which facilitates server-side UNIX domain socket
   * communication for this transport.
   *
   * If this transport does not support communication via domain sockets, `null` is returned
   * instead.
   *
   * The default implementation of this property returns null thus indicating no support for UNIX
   * domain sockets.
   */
  val serverDomainSocketChannelType: KClass<out ServerDomainSocketChannel>?
    get() = null

  /**
   * Constructs a new event loop group with the desired number of threads and thread factory.
   *
   * When zero is given for [nThreads], the implementation should choose a suitable number of
   * threads for network purposes (typically the number of available logical cores on the system).
   *
   * When null is given for [threadFactory], the implementation should choose a suitable thread
   * factory to facilitate the creation of its pool.
   *
   * The resulting event loop is expected to be compatible with all channel types returned by the
   * attributes defined within this interface.
   */
  fun createEventLoopGroup(nThreads: Int = 0, threadFactory: ThreadFactory? = null): EventLoopGroup
}
