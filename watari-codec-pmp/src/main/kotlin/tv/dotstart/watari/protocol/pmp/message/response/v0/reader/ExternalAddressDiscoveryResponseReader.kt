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
package tv.dotstart.watari.protocol.pmp.message.response.v0.reader

import tv.dotstart.watari.protocol.pmp.message.response.reader.ResponseReader
import tv.dotstart.watari.protocol.pmp.message.response.v0.ExternalAddressDiscoveryResponse
import tv.dotstart.watari.protocol.pmp.message.wire.WireResponse
import java.net.Inet4Address

/**
 * Provides a response reader for external address discovery responses and broadcasts.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
object ExternalAddressDiscoveryResponseReader : ResponseReader<ExternalAddressDiscoveryResponse> {

  private const val MIN_BYTES = 4

  override val opcode: UByte
    get() = ExternalAddressDiscoveryResponse.OPCODE

  override fun decode(msg: WireResponse): ExternalAddressDiscoveryResponse {
    with(msg.content()) {
      require(isReadable(MIN_BYTES)) { "Expected at least $MIN_BYTES bytes within response but got ${readableBytes()}" }

      val externalAddress = ByteArray(4)
        .also(::readBytes)
        .let(Inet4Address::getByAddress)
          as Inet4Address

      return ExternalAddressDiscoveryResponse(
        msg.sender(),
        msg.recipient(),
        msg.code,
        msg.secondsSinceEpoch,
        externalAddress
      )
    }
  }
}
