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

import tv.dotstart.watari.common.NetworkProtocol
import tv.dotstart.watari.protocol.pmp.message.request.Request
import java.net.InetSocketAddress
import java.time.Duration

/**
 * Represents a request to expose a given port with a specific protocol.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
sealed interface PortMappingRequest : Request {

  companion object {

    /**
     * Defines the recommended TTL which should be passed when no value is given explicitly.
     */
    val RECOMMENDED_TTL = Duration.ofHours(2)

    fun tcp(
      sender: InetSocketAddress, recipient: InetSocketAddress,
      internalPort: UShort, suggestedExternalPort: UShort?, timeToLive: Duration
    ) =
      TcpPortMappingRequest(sender, recipient, internalPort, suggestedExternalPort, timeToLive)

    fun tcp(
      sender: InetSocketAddress, recipient: InetSocketAddress,
      internalPort: UShort, timeToLive: Duration
    ) =
      this.tcp(sender, recipient, internalPort, null, timeToLive)

    fun removeTcp(sender: InetSocketAddress, recipient: InetSocketAddress, internalPort: UShort) =
      this.tcp(sender, recipient, internalPort, null, Duration.ZERO)

    fun udp(
      sender: InetSocketAddress, recipient: InetSocketAddress,
      internalPort: UShort, suggestedExternalPort: UShort?, timeToLive: Duration
    ) =
      UdpPortMappingRequest(sender, recipient, internalPort, suggestedExternalPort, timeToLive)

    fun udp(
      sender: InetSocketAddress, recipient: InetSocketAddress,
      internalPort: UShort, timeToLive: Duration
    ) =
      this.udp(sender, recipient, internalPort, null, timeToLive)

    fun removeUdp(
      sender: InetSocketAddress, recipient: InetSocketAddress,
      internalPort: UShort
    ) =
      this.udp(sender, recipient, internalPort, null, Duration.ZERO)
  }

  /**
   * Identifies the protocol which is being mapped by this request.
   */
  val protocol: NetworkProtocol

  /**
   * Identifies the port on which the target service is listening on the local host.
   */
  val internalPort: UShort

  /**
   * Identifies the port on which the requesting peer would like the service to be exposed.
   */
  val suggestedExternalPort: UShort?

  /**
   * Identifies the time-to-live for this mapping (in seconds).
   */
  val timeToLive: Duration

}
