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
package tv.dotstart.watari.protocol.pmp.codec

import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import tv.dotstart.watari.protocol.pmp.message.Message
import tv.dotstart.watari.protocol.pmp.message.registry.MessageWriterRegistry

/**
 * Converts wire messages to their respective fully encoded representation using a predefined
 * message writer registry.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
class MessageEncoder<DM : Message>(
  val registry: MessageWriterRegistry<DM, *>
) : MessageToMessageEncoder<DM>(registry.decodedType.java) {

  override fun encode(ctx: ChannelHandlerContext, msg: DM, out: MutableList<Any>) {
    val writer = this.registry.writerFor(msg::class)
      ?: throw IllegalArgumentException("Attempted to transmit message of unknown type ${msg::class}")

    try {
      out += writer.encode(ctx.alloc(), msg)
    } catch (ex: IllegalArgumentException) {
      throw IllegalArgumentException("Failed to encode message of type ${msg::class}", ex)
    }
  }
}
