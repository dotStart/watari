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
import io.netty.handler.codec.MessageToMessageDecoder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import tv.dotstart.watari.protocol.pmp.message.registry.MessageReaderRegistry
import tv.dotstart.watari.protocol.pmp.message.wire.WireMessage

/**
 * Converts previously decoded wire messages to their respective fully decoded representation using
 * a predefined message reader registry.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
class MessageDecoder<WM : WireMessage>(
  val registry: MessageReaderRegistry<*, WM>
) : MessageToMessageDecoder<WM>(registry.wireType.java) {

  companion object {

    private val logger: Logger by lazy { LogManager.getLogger(MessageDecoder::class.java) }
  }

  override fun decode(ctx: ChannelHandlerContext, msg: WM, out: MutableList<Any>) {
    val decoder = this.registry.readerFor(msg.opcode)
    if (decoder == null) {
      // TODO: This behavior depends on whether the codec is running in server or client mode
      //       fire a user event for downstream handlers to take care of?
      logger.debug("Received message with unknown opcode ${msg.opcode} - Ignoring")
      return
    }

    try {
      out += decoder.decode(msg)
    } catch (ex: IllegalArgumentException) {
      logger.debug("Failed to decode message ${msg.opcode}", ex)
    }
  }
}
