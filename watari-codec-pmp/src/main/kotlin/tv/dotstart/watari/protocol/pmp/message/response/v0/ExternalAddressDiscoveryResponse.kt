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

import tv.dotstart.watari.protocol.pmp.message.response.Response
import tv.dotstart.watari.protocol.pmp.message.response.ResponseCode
import java.net.Inet4Address
import java.net.InetSocketAddress

/**
 * Encapsulates a response or broadcast of the external gateway address.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
data class ExternalAddressDiscoveryResponse(
  override val sender: InetSocketAddress,
  override val recipient: InetSocketAddress,
  override val code: ResponseCode,
  override val secondsSinceEpoch: UInt,
  val externalAddress: Inet4Address
) : Response {

  companion object {

    const val OPCODE: UByte = Response.BASE_OPCODE
  }
}
