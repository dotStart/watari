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
import io.netty.channel.AddressedEnvelope
import java.net.InetSocketAddress

/**
 * Represents a request or response message within the port mapping protocol.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
interface WireMessage : AddressedEnvelope<ByteBuf, InetSocketAddress> {

  /**
   * Identifies the numeric revision of the protocol.
   */
  val version: UByte

  /**
   * Identifies the operation to which this message requests or responds to.
   */
  val opcode: UByte

  override fun refCnt(): Int = this.content().refCnt()

  override fun retain() = this.apply {
    this.content().retain()
  }

  override fun retain(increment: Int) = this.apply {
    this.content().retain(increment)
  }

  override fun touch() = this.apply {
    this.content().touch()
  }

  override fun touch(hint: Any?) = this.apply {
    this.content().touch(hint)
  }

  override fun release(): Boolean = this.content().release()

  override fun release(decrement: Int): Boolean = this.content().release(decrement)
}
