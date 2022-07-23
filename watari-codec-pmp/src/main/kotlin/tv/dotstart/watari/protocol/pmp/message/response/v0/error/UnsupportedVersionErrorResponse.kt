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
package tv.dotstart.watari.protocol.pmp.message.response.v0.error

import tv.dotstart.watari.protocol.pmp.message.response.Response
import tv.dotstart.watari.protocol.pmp.message.response.ResponseCode
import java.net.InetSocketAddress

/**
 * Represents a response which indicates that the previously requested protocol version is not
 * supported by the responding party.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
data class UnsupportedVersionErrorResponse(
  override val sender: InetSocketAddress,
  override val recipient: InetSocketAddress,
  override val secondsSinceEpoch: UInt
) : Response {

  override val code = ResponseCode.UNSUPPORTED_VERSION
}
