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
package tv.dotstart.watari.protocol.pmp.message.response.v0.writer

import io.netty.buffer.ByteBufAllocator
import tv.dotstart.watari.protocol.pmp.message.response.v0.PortMappingResponse
import tv.dotstart.watari.protocol.pmp.message.response.v0.TcpPortMappingResponse
import tv.dotstart.watari.protocol.pmp.message.response.v0.UdpPortMappingResponse
import tv.dotstart.watari.protocol.pmp.message.response.writer.ResponseWriter
import tv.dotstart.watari.protocol.pmp.message.wire.WireResponse
import kotlin.math.roundToInt

/**
 * Provides a writer for TCP and UDP port mapping responses.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
object PortMappingResponseWriter : ResponseWriter<PortMappingResponse> {

  override val type = PortMappingResponse::class

  override fun encode(alloc: ByteBufAllocator, msg: PortMappingResponse): WireResponse {
    val opcode = when (msg) {
      is TcpPortMappingResponse -> TcpPortMappingResponse.OPCODE
      is UdpPortMappingResponse -> UdpPortMappingResponse.OPCODE
    }

    val payload = alloc.buffer(8)
      .writeShort(msg.internalPort.toInt())
      .writeShort(msg.externalPort.toInt())
      .writeInt((msg.timeToLive.toMillis() / 1_000.0).roundToInt())

    return WireResponse(
      msg.sender, msg.recipient,
      0u, opcode, msg.code, msg.secondsSinceEpoch, payload
    )
  }
}
