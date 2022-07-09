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

import io.netty.channel.epoll.*
import java.util.concurrent.ThreadFactory

/**
 * Provides a transport implementation which relies on the Linux `epoll` function in order to
 * facilitate network handling.
 *
 * This transport is available for a select subset of supported architectures on Linux based
 * operating systems.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
class EpollTransport : Transport {

  override val id = "epoll"

  override val native = true
  override val available by lazy(Epoll::isAvailable)

  override val socketChannelType = EpollSocketChannel::class
  override val serverSocketChannelType = EpollServerSocketChannel::class

  override val datagramChannelType = EpollDatagramChannel::class

  override val domainSocketChannelType = EpollDomainSocketChannel::class
  override val serverDomainSocketChannelType = EpollServerDomainSocketChannel::class

  override fun createEventLoopGroup(nThreads: Int, threadFactory: ThreadFactory?) =
    EpollEventLoopGroup(nThreads, threadFactory)
}
