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
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageDecoder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Provides a basis for wire decoders of port mapping protocol messages.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
abstract class AbstractWireMessageDecoder : MessageToMessageDecoder<DatagramPacket>() {

  protected val logger: Logger by lazy { LogManager.getLogger(this::class.java) }

  companion object {

    /**
     * Identifies the minimum number of bytes within the shared section of the message.
     *
     * The shared section always contains a minimum of two fields:
     *
     *  - Version
     *  - Opcode
     *
     * In addition to type and operation specific contents.
     */
    private const val MIN_BYTES = 1 + 1;
  }

  override fun decode(ctx: ChannelHandlerContext, msg: DatagramPacket, out: MutableList<Any>) {
    with(msg.content()) {
      if (!isReadable(MIN_BYTES)) {
        logger.debug("Received invalid message from ${msg.sender()} (addressed to ${msg.recipient()}): Message with insufficient data - Expected at least $MIN_BYTES but got ${readableBytes()} - Ignored")
        return
      }

      val version = readByte().toUByte()
      val opcode = readByte().toUByte()

      decode(ctx, version, opcode, msg, out)
    }
  }

  /**
   * Decodes the type specific contents of the PMP message.
   */
  protected abstract fun decode(
    ctx: ChannelHandlerContext,
    version: UByte,
    opcode: UByte,
    msg: DatagramPacket,
    out: MutableList<Any>
  )
}
