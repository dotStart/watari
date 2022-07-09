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

import io.netty.channel.kqueue.*
import java.util.concurrent.ThreadFactory

/**
 * Provides a transport which relies on the FreeBSD `KQueue` APIs in order to facilitate network
 * handling.
 *
 * This transport is available on all actively supported architectures of OS X (Note that support
 * for x86_64 based systems may be dropped in the future).
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
class KQueueTransport : Transport {

  override val id = "kqueue"
  override val name = "KQueue"

  override val native = true
  override val available by lazy(KQueue::isAvailable)

  override val socketChannelType = KQueueSocketChannel::class
  override val serverSocketChannelType = KQueueServerSocketChannel::class
  override val datagramChannelType = KQueueDatagramChannel::class

  override val domainSocketChannelType = KQueueDomainSocketChannel::class
  override val serverDomainSocketChannelType = KQueueServerDomainSocketChannel::class

  override fun createEventLoopGroup(nThreads: Int, threadFactory: ThreadFactory?) =
    KQueueEventLoopGroup(nThreads, threadFactory)
}
