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
package tv.dotstart.watari.protocol.pmp.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageEncoder
import tv.dotstart.watari.protocol.pmp.message.wire.WireMessage

/**
 * Provides a basis for wire encoders of port mapping protocol messages.
 *
 * @author Johannes Donath
 * @date 23/07/2022
 * @since 0.1.0
 */
abstract class AbstractWireMessageEncoder<M : WireMessage> : MessageToMessageEncoder<M>() {

  override fun encode(ctx: ChannelHandlerContext, msg: M, out: MutableList<Any>) {
    val buf = ctx.alloc().buffer(2)
    this.encode(ctx, msg, buf)

    out += DatagramPacket(buf, msg.recipient(), msg.sender())
  }

  protected open fun encode(ctx: ChannelHandlerContext, msg: M, out: ByteBuf) {
    out
      .writeByte(msg.version.toInt())
      .writeByte(msg.opcode.toInt())
  }
}
