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

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import kotlin.reflect.KClass

/**
 * Provides Kotlin friendly extension functions to simplify the creation of channels.
 *
 * @author Johannes Donath
 * @date 13/07/2022
 * @since 0.1.0
 */

/**
 * Kotlin friendly shorthand for [Bootstrap.channel]
 *
 * @see Bootstrap.channel
 */
fun Bootstrap.channel(type: KClass<out Channel>) = this.apply {
  this.channel(type.java)
}

/**
 * Shorthand function which configures [Transport.socketChannelType] as the channel type for a given
 * bootstrap.
 */
fun Bootstrap.socketChannel(transport: Transport) =
  this.channel(transport.socketChannelType)

/**
 * Shorthand function which configures [Transport.datagramChannelType] as the channel type for a
 * given bootstrap.
 */
fun Bootstrap.datagramChannel(transport: Transport) =
  this.channel(transport.datagramChannelType)

/**
 * Shorthand function which configures [Transport.domainSocketChannelType] as the channel type for
 * a given bootstrap.
 *
 * @throws IllegalArgumentException when the specified [transport] does not support domain sockets.
 */
fun Bootstrap.domainSocketChannel(transport: Transport) =
  this.channel(
    transport.domainSocketChannelType
      ?: throw IllegalArgumentException("Unsupported transport: ${transport.name} (${transport.id}) does not support domain sockets")
  )
