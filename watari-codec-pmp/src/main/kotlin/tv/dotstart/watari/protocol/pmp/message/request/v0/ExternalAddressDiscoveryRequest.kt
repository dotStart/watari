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
package tv.dotstart.watari.protocol.pmp.message.request.v0

import tv.dotstart.watari.protocol.pmp.message.request.Request
import java.net.InetSocketAddress

/**
 * Requests the externally visible address of the gateway.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
data class ExternalAddressDiscoveryRequest(
  override val sender: InetSocketAddress,
  override val recipient: InetSocketAddress
) : Request {

  companion object {

    const val OPCODE: UByte = 0u
  }
}
