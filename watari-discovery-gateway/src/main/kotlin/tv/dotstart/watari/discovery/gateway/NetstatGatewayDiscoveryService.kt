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
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.util.*

/**
 * Resolves the currently active default gateway by invoking `netstat`.
 *
 * This implementation is provided as a fallback gateway discovery provider as the JDK does not
 * currently provide a system agnostic way of retrieving the address of the gateway.
 *
 * Note that `netstat` is commonly available across most operating systems including Linux, Mac OS
 * and Windows thus making this a suitable fallback. If the command is unavailable or returns an
 * incompatible output, `null` is returned instead thus permitting other discovery mechanisms to
 * take over.
 *
 * @author Johannes Donath
 * @date 14/07/2022
 * @since 0.1.0
 */
class NetstatGatewayDiscoveryService : GatewayDiscoveryService {

  override val id = "netstat"
  override val priority = Int.MIN_VALUE + 1

  companion object {

    private val logger by lazy { LogManager.getLogger(NetstatGatewayDiscoveryService::class) }
  }

  override fun discover(): Pair<NetworkInterface, InetAddress>? {
    val output = this.callNetstat()
      ?: return null

    val tokens = output
      .map(String::trim)
      .firstOrNull { it.startsWith("default") || it.startsWith("0.0.0.0") }
      ?.let(::StringTokenizer)
      ?.takeIf { it.countTokens() >= 4 }
      ?: return null

    (0 until 2).forEach { tokens.nextToken() }

    val gatewayAddress = tokens.nextToken()
      .let {
        try {
          InetAddress.getByName(it)
        } catch (ex: UnknownHostException) {
          logger.warn("Failed to resolve gateway provided by netstat via address '$it'", ex)
          return null
        } catch (ex: SecurityException) {
          logger.warn("Failed to resolve gateway provided by netstat via address '$it'", ex)
          return null
        }
      }
    val iface = tokens.nextToken()
      .let {
        try {
          InetAddress.getByName(it)
        } catch (ex: UnknownHostException) {
          logger.warn("Failed to resolve interface provided by netstat via address '$it'", ex)
          return null
        } catch (ex: SecurityException) {
          logger.warn("Failed to resolve interface provided by netstat via address '$it'", ex)
          return null
        }
      }
      .let {
        try {
          NetworkInterface.getByInetAddress(it)
        } catch (ex: IOException) {
          logger.warn("Failed to resolve interface provided by netstat via address '$it'", ex)
          return null
        }
      }
      ?: return null

    return iface to gatewayAddress
  }

  private fun callNetstat(): List<String>? {
    val factory = ProcessBuilder()
      .command("netstat", "-rn")

    val process = try {
      factory.start()
    } catch (ex: SecurityException) {
      logger.info(
        "Cannot invoke netstat due to security restrictions - Cannot discover gateway address",
        ex
      )
      return null
    } catch (ex: IOException) {
      logger.info("Failed to invoke netstat - Cannot discover gateway address", ex)
      return null
    }

    try {
      process.waitFor()
    } catch (ex: InterruptedException) {
      logger.warn("Interrupted during netstat gateway discovery")
    }

    return BufferedReader(InputStreamReader(process.inputStream))
      .use { it.lineSequence().toList() }
  }
}
