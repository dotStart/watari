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
import tv.dotstart.watari.common.service.Service
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
interface Transport : Service {

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
