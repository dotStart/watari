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
import tv.dotstart.watari.protocol.pmp.message.request.v0.PortMappingRequest
import tv.dotstart.watari.protocol.pmp.message.wire.WireRequest
import java.net.InetSocketAddress
import java.time.Duration

/**
 * Provides a basis for request readers which decode the POKO representations of port mapping
 * requests.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
abstract class AbstractPortMappingRequestReader<out DM : PortMappingRequest> : RequestReader<DM> {

  companion object {

    /**
     * Defines the minimum amount of bytes required within a valid wire representation for a port
     * mapping request.
     *
     * Port mapping requests (regardless of protocol type) always contain the following 3 fields:
     *
     *  - Internal Port
     *  - Suggested External Port
     *  - Time To Live (in Seconds)
     */
    private const val MIN_BYTES = 2 + 2 + 4
  }

  override fun decode(msg: WireRequest): DM {
    require(msg.version == 0u.toUByte()) { "Expected v1 request but got v${msg.version}" }

    with(msg.content()) {
      require(
        isReadable(
          MIN_BYTES
        )
      ) { "Expected at least $MIN_BYTES bytes within mapping request but got ${readableBytes()}" }

      val internalPort = readShort().toUShort()
      val suggestedExternalPort = readShort().toUShort()
        .takeIf { it != 0u.toUShort() }
      val timeToLive = Duration.ofSeconds(readUnsignedInt())

      return create(msg.sender(), msg.recipient(), internalPort, suggestedExternalPort, timeToLive)
    }
  }

  protected abstract fun create(
    sender: InetSocketAddress,
    recipient: InetSocketAddress,
    internalPort: UShort,
    suggestedExternalPort: UShort?,
    timeToLive: Duration
  ): DM
}
