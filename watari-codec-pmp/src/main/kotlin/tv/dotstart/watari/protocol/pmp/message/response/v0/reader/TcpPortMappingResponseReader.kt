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

import tv.dotstart.watari.protocol.pmp.message.response.ResponseCode
import tv.dotstart.watari.protocol.pmp.message.response.v0.TcpPortMappingResponse
import java.net.InetSocketAddress
import java.time.Duration

/**
 * Provides a reader for TCP port mapping responses.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
object TcpPortMappingResponseReader : AbstractPortMappingResponseReader<TcpPortMappingResponse>() {

  override val opcode: UByte
    get() = TcpPortMappingResponse.OPCODE

  override fun create(
    sender: InetSocketAddress,
    recipient: InetSocketAddress,
    code: ResponseCode,
    secondsSinceEpoch: UInt,
    internalPort: UShort,
    externalPort: UShort,
    timeToLive: Duration
  ) = TcpPortMappingResponse(
    sender,
    recipient,
    code,
    secondsSinceEpoch,
    internalPort,
    externalPort,
    timeToLive
  )
}
