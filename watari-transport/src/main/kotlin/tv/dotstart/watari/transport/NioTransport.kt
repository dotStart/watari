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

import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import java.util.concurrent.ThreadFactory

/**
 * Provides a JDK based transport implementation based on the NIO (New IO) interface introduced with
 * JDK 1.7.
 *
 * This implementation is available in all execution environments but provides worse performance
 * compared to the native transport implementations supplied by this module.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
class NioTransport : Transport {

  override val id = "nio"
  override val name = "NIO"

  override val priority = Int.MIN_VALUE

  override val socketChannelType = NioSocketChannel::class
  override val serverSocketChannelType = NioServerSocketChannel::class

  override val datagramChannelType = NioDatagramChannel::class

  override fun createEventLoopGroup(nThreads: Int, threadFactory: ThreadFactory?) =
    NioEventLoopGroup(nThreads, threadFactory)
}
