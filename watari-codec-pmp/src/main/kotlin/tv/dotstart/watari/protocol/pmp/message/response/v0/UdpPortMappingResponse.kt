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
package tv.dotstart.watari.protocol.pmp.message.response.v0

import tv.dotstart.watari.common.NetworkProtocol
import tv.dotstart.watari.protocol.pmp.message.response.Response
import tv.dotstart.watari.protocol.pmp.message.response.ResponseCode
import java.net.InetSocketAddress
import java.time.Duration

/**
 * Encapsulates a response to a prior UDP port mapping request.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
data class UdpPortMappingResponse(
  override val sender: InetSocketAddress,
  override val recipient: InetSocketAddress,
  override val code: ResponseCode,
  override val secondsSinceEpoch: UInt,
  override val internalPort: UShort,
  override val externalPort: UShort,
  override val timeToLive: Duration
) : PortMappingResponse {

  override val protocol = NetworkProtocol.UDP

  companion object {

    val OPCODE: UByte = (Response.BASE_OPCODE + 1u).toUByte()
  }
}
