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

import tv.dotstart.watari.protocol.pmp.message.registry.AbstractMessageCodecRegistry
import tv.dotstart.watari.protocol.pmp.message.request.Request
import tv.dotstart.watari.protocol.pmp.message.request.v0.reader.ExternalAddressDiscoveryRequestReader
import tv.dotstart.watari.protocol.pmp.message.request.v0.reader.TcpPortMappingRequestReader
import tv.dotstart.watari.protocol.pmp.message.request.v0.reader.UdpPortMappingRequestReader
import tv.dotstart.watari.protocol.pmp.message.request.v0.writer.ExternalAddressDiscoveryRequestWriter
import tv.dotstart.watari.protocol.pmp.message.request.v0.writer.PortMappingRequestWriter
import tv.dotstart.watari.protocol.pmp.message.wire.WireRequest

/**
 * Provides a registry of v1 request readers and writers.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
object RequestCodecRegistry : AbstractMessageCodecRegistry<Request, WireRequest>(
  listOf(
    ExternalAddressDiscoveryRequestReader,
    TcpPortMappingRequestReader,
    UdpPortMappingRequestReader
  ),

  listOf(
    ExternalAddressDiscoveryRequestWriter,
    PortMappingRequestWriter
  )
) {

  override val wireType = WireRequest::class
  override val decodedType = Request::class
}