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
import tv.dotstart.watari.protocol.pmp.message.response.v0.ExternalAddressDiscoveryResponse
import tv.dotstart.watari.protocol.pmp.message.response.writer.ResponseWriter
import tv.dotstart.watari.protocol.pmp.message.wire.WireResponse

/**
 * Provides a writer for external address discovery responses.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
object ExternalAddressDiscoveryResponseWriter : ResponseWriter<ExternalAddressDiscoveryResponse> {

  override val type = ExternalAddressDiscoveryResponse::class

  override fun encode(
    alloc: ByteBufAllocator,
    msg: ExternalAddressDiscoveryResponse
  ): WireResponse {
    val payload = alloc.buffer(4)
      .writeBytes(msg.externalAddress.address)

    return WireResponse(
      msg.sender,
      msg.recipient,
      0u,
      ExternalAddressDiscoveryResponse.OPCODE,
      msg.code,
      msg.secondsSinceEpoch,
      payload
    )
  }
}
