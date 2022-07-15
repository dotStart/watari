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
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

/**
 * @author Johannes Donath
 * @date 14/07/2022
 * @since 0.1.0
 */
internal class GuessingGatewayDiscoveryProviderTest {

  @Test
  fun `It should return values`() {
    val result = GuessingGatewayDiscoveryService().discover()

    assumeTrue(result != null, "Could not locate gateway address")
    if (result == null) {
      return
    }

    val (iface, addr) = result

    // FIXME: Since we have no idea about the network configuration on the executing agent, we'll
    //        simply validate that we got values at all - There may be a better way to test this in
    //        the future (e.g. by cross referencing other providers)
    assertNotNull(iface)
    assertNotNull(addr)
  }
}
