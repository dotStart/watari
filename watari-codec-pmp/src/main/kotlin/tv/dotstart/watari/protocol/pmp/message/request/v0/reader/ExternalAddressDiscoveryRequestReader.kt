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
package tv.dotstart.watari.protocol.pmp.message.request.v0.reader

import tv.dotstart.watari.protocol.pmp.message.request.reader.RequestReader
import tv.dotstart.watari.protocol.pmp.message.request.v0.ExternalAddressDiscoveryRequest
import tv.dotstart.watari.protocol.pmp.message.wire.WireRequest

/**
 * Decodes external address discovery requests.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
object ExternalAddressDiscoveryRequestReader : RequestReader<ExternalAddressDiscoveryRequest> {

  override val opcode: UByte
    get() = ExternalAddressDiscoveryRequest.OPCODE

  override fun decode(msg: WireRequest): ExternalAddressDiscoveryRequest {
    require(msg.version == 0u.toUByte()) { "Expected v1 request but got v${msg.version}" }

    with(msg.content()) {
      require(!isReadable) { "Expected empty payload but got ${readableBytes()} bytes" }
    }

    return ExternalAddressDiscoveryRequest(msg.sender(), msg.recipient())
  }
}
