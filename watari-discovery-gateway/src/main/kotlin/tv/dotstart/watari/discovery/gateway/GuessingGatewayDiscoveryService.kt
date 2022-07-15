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
package tv.dotstart.watari.discovery.gateway

import org.apache.logging.log4j.LogManager
import java.net.*

/**
 * Provides a discovery provider implementation which guesses the gateway address for a given
 * interface based on its respective network mask.
 *
 * Note that this implementation is incredibly inaccurate but supports the most common network
 * configuration while supporting all execution environments.
 *
 * @author Johannes Donath
 * @date 14/07/2022
 * @since 0.1.0
 */
class GuessingGatewayDiscoveryService : GatewayDiscoveryService {

  override val id = "guess"
  override val name = "Guess"
  override val priority = Int.MIN_VALUE

  companion object {

    private val logger by lazy { LogManager.getLogger(GuessingGatewayDiscoveryService::class.java) }
  }

  override fun discover(): Pair<NetworkInterface, InetAddress>? {
    val gatewayIface = this.findGatewayInterface()
      ?: this.guessGatewayInterface()
      ?: return null

    // once we found a qualifying interface, we'll attempt to find a site local address which has
    // been assigned to it as this is going to be the most likely to give access to the gateway
    val localAddress = gatewayIface.interfaceAddresses
      .let {
        it.firstOrNull { it.address is Inet4Address && it.address.isSiteLocalAddress }
          ?: it.firstOrNull()
      }
      ?: return null

    // in some rare cases, the network prefix may be reported as 32 thus defining no network - this
    // algorithm does not work with these configurations since the gateway will be outside of our
    // own network
    if (localAddress.networkPrefixLength == 32.toShort()) {
      return null
    }

    // we'll now simply choose the first address within the allocated address space as a possible
    // gateway address - this is the most common configuration in the wild thus giving us a decent
    // shot at guessing the right address
    var mask = 1
    (1 until localAddress.networkPrefixLength.toInt())
      .forEach {
        mask = mask shl 1 xor 1
      }
    mask = mask shl (32 - localAddress.networkPrefixLength)

    val maskA = mask ushr 24
    val maskB = mask ushr 16 and 0xFF
    val maskC = mask ushr 8 and 0xFF
    val maskD = mask and 0xFF

    val bytes = localAddress.address.address
    val byteA = (bytes[0].toInt() and maskA).toByte()
    val byteB = (bytes[1].toInt() and maskB).toByte()
    val byteC = (bytes[2].toInt() and maskC).toByte()
    val byteD = (bytes[3].toInt() and maskD).toByte()

    try {
      return gatewayIface to InetAddress.getByAddress(
        byteArrayOf(
          byteA,
          byteB,
          byteC,
          (byteD + 1).toByte()
        )
      )
    } catch (ex: UnknownHostException) {
      logger.debug("Failed to construct guessed address: $byteA.$byteB.$byteC.$byteC + 1")
      return null
    }
  }

  /**
   * Attempts to locate the interface which provides internet access by opening a datagram
   * socket.
   */
  private fun findGatewayInterface(): NetworkInterface? = try {
    DatagramSocket()
      .use {
        it.connect(InetSocketAddress(InetAddress.getByAddress(byteArrayOf(8, 8, 8, 8)), 51))
        it.localAddress
      }
      .let(NetworkInterface::getByInetAddress)
  } catch (ex: SocketException) {
    logger.warn(
      "Failed to create datagram socket to acquire gateway interface: Connection failed",
      ex
    )
    null
  } catch (ex: SecurityException) {
    logger.warn(
      "Failed to create datagram socket to acquire gateway interface: Permission denied",
      ex
    )

    null
  }

  /**
   * Attempts to locate the interface which provides internet access by finding an interface which
   * has been assigned a site-local address (e.g. from the 10.0.0.0/8, 172.16.0.0/16 or
   * 192.168.0.0/16 range).
   */
  private fun guessGatewayInterface(): NetworkInterface? =
    NetworkInterface.getNetworkInterfaces().asSequence()
      .firstOrNull {
        it.inetAddresses.asSequence()
          .filter { it is Inet4Address && it.isSiteLocalAddress }
          .any()
      }
}
