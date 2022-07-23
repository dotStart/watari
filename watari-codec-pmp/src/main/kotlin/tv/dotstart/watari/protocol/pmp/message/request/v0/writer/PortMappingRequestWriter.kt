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
package tv.dotstart.watari.protocol.pmp.message.request.v0.writer

import io.netty.buffer.ByteBufAllocator
import tv.dotstart.watari.protocol.pmp.message.request.v0.PortMappingRequest
import tv.dotstart.watari.protocol.pmp.message.request.v0.TcpPortMappingRequest
import tv.dotstart.watari.protocol.pmp.message.request.v0.UdpPortMappingRequest
import tv.dotstart.watari.protocol.pmp.message.request.writer.RequestWriter
import tv.dotstart.watari.protocol.pmp.message.wire.WireRequest
import kotlin.math.roundToInt

/**
 * Encodes TCP and UDP port mapping requests.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
object PortMappingRequestWriter : RequestWriter<PortMappingRequest> {

  override val type = PortMappingRequest::class

  override fun encode(alloc: ByteBufAllocator, msg: PortMappingRequest): WireRequest {
    val opcode = when (msg) {
      is UdpPortMappingRequest -> UdpPortMappingRequest.OPCODE
      is TcpPortMappingRequest -> TcpPortMappingRequest.OPCODE
    }

    val payload = alloc.buffer(8)
      .writeShort(msg.internalPort.toInt())
      .writeShort(msg.suggestedExternalPort?.toInt() ?: 0)
      .writeInt((msg.timeToLive.toMillis() / 1_000.0).roundToInt())

    return WireRequest(msg.sender, msg.recipient, 0u, opcode, payload)
  }
}
