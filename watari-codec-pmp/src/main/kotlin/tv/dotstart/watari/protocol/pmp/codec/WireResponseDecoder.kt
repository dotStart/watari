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
import tv.dotstart.watari.protocol.pmp.message.response.ResponseCode
import tv.dotstart.watari.protocol.pmp.message.wire.WireResponse

/**
 * Decodes wire responses from their datagram packet representation.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
class WireResponseDecoder : AbstractWireMessageDecoder() {

  companion object {

    /**
     * Identifies the minimum number of bytes in a server response.
     *
     * Server responses always consist of a minimum of 4 fields:
     *
     *  - Response Code
     *  - Seconds since Epoch
     *
     * In addition to the operation specific contents.
     */
    private const val MIN_BYTES = 2 + 4
  }

  override fun decode(
    ctx: ChannelHandlerContext,
    version: UByte,
    opcode: UByte,
    msg: DatagramPacket,
    out: MutableList<Any>
  ) {
    with(msg.content()) {
      if (opcode < 128u) {
        logger.debug("Received invalid message from ${msg.sender()} (addressed to ${msg.recipient()}): Opcode $opcode within request range")
        return
      }

      if (!isReadable(MIN_BYTES)) {
        logger.debug("Received invalid response from ${msg.sender()} (addressed to ${msg.recipient()}): Response with insufficient data - Expected at least $MIN_BYTES but got ${readableBytes()} - Ignored")
        return
      }

      val code = readShort().toUShort()
        .let(ResponseCode.Companion::byCode)
      val secondsSinceEpoch = readInt().toUInt()
      val payload = readRetainedSlice(readableBytes())

      out += WireResponse(
        msg.sender(),
        msg.recipient(),
        version,
        opcode,
        code,
        secondsSinceEpoch,
        payload
      )
    }
  }
}
