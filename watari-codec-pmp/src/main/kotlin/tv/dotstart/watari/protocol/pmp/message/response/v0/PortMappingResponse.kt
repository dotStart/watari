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
import java.time.Duration

/**
 * Represents a response to a request to expose a given port with a specific protocol.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
sealed interface PortMappingResponse : Response {

  /**
   * Identifies the protocol which has been mapped.
   */
  val protocol: NetworkProtocol

  /**
   * Identifies the port on which the target service is listening on the local host.
   */
  val internalPort: UShort

  /**
   * Identifies the port on which the service has been exposed.
   */
  val externalPort: UShort

  /**
   * Identifies the time-to-live for this mapping (in seconds).
   */
  val timeToLive: Duration
}
