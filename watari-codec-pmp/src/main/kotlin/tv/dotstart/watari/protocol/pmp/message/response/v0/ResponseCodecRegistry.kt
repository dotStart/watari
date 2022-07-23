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

import tv.dotstart.watari.protocol.pmp.message.registry.AbstractMessageCodecRegistry
import tv.dotstart.watari.protocol.pmp.message.response.Response
import tv.dotstart.watari.protocol.pmp.message.response.v0.reader.ExternalAddressDiscoveryResponseReader
import tv.dotstart.watari.protocol.pmp.message.response.v0.reader.TcpPortMappingResponseReader
import tv.dotstart.watari.protocol.pmp.message.response.v0.reader.UdpPortMappingResponseReader
import tv.dotstart.watari.protocol.pmp.message.response.v0.writer.ExternalAddressDiscoveryResponseWriter
import tv.dotstart.watari.protocol.pmp.message.response.v0.writer.PortMappingResponseWriter
import tv.dotstart.watari.protocol.pmp.message.wire.WireResponse

/**
 * Provides a registry of response readers and writers.
 *
 * @author Johannes Donath
 * @date 09/07/2022
 */
object ResponseCodecRegistry : AbstractMessageCodecRegistry<Response, WireResponse>(
  listOf(
    ExternalAddressDiscoveryResponseReader,
    TcpPortMappingResponseReader,
    UdpPortMappingResponseReader
  ),

  listOf(
    ExternalAddressDiscoveryResponseWriter,
    PortMappingResponseWriter
  )
) {

  override val wireType = WireResponse::class
  override val decodedType = Response::class
}
