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

import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.DatagramSocket
import java.net.NetworkInterface
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * @author Johannes Donath
 * @date 14/07/2022
 * @since 0.1.0
 */
internal class NetstatGatewayDiscoveryProviderTest {

  private lateinit var provider: GatewayDiscoveryService

  @BeforeEach
  fun prepareProvider() {
    this.provider = NetstatGatewayDiscoveryService()
  }

  @Test
  fun `It should return values`() {
    val result = this.provider.discover()

    assumeTrue(result != null)
    if (result == null) {
      return
    }

    val (iface, addr) = result

    // FIXME: Since we have no idea about the network configuration on the executing agent, we'll
    //        simply validate that we got values at all - There may be a better way to test this in
    //        the future (e.g. by cross referencing other providers)
    assertNotNull(iface)
    assertNotNull(addr)

    // Make sure that the detected interface at least matches the interface chosen by the JDK when
    // attempting to establish a connection
    //
    // Note that this call does not actually establish any connections or exchange messages, the
    // desired attributes will be exposed to us regardless
    val detectedIface = DatagramSocket()
      .use {
        it.connect(addr, 51)
        it.localAddress
      }
      .let(NetworkInterface::getByInetAddress)

    assertEquals(detectedIface, iface)
  }
}
