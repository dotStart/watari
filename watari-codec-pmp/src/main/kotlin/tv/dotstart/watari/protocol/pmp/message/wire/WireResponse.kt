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
package tv.dotstart.watari.protocol.pmp.message.wire

import io.netty.buffer.ByteBuf
import tv.dotstart.watari.protocol.pmp.message.response.ResponseCode
import java.net.InetSocketAddress

/**
 * @author Johannes Donath
 * @date 08/07/2022
 */
data class WireResponse(
  private val sender: InetSocketAddress,
  private val recipient: InetSocketAddress,
  override val version: UByte,
  override val opcode: UByte,

  /**
   * Identifies the result of the previous operation.
   */
  val code: ResponseCode,

  /**
   * Identifies the amount of time (in seconds) which has passed since the gateway has started.
   */
  val secondsSinceEpoch: UInt,

  private val payload: ByteBuf
) : WireMessage {

  override fun content() = this.payload

  override fun sender() = this.sender

  override fun recipient() = this.recipient
}
