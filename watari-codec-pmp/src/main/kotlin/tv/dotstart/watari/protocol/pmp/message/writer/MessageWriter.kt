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
package tv.dotstart.watari.protocol.pmp.message.writer

import io.netty.buffer.ByteBufAllocator
import tv.dotstart.watari.protocol.pmp.message.Message
import tv.dotstart.watari.protocol.pmp.message.wire.WireMessage
import kotlin.reflect.KClass

/**
 * @author Johannes Donath
 * @date 08/07/2022
 */
interface MessageWriter<DM : Message, out WM : WireMessage> {

  /**
   * Identifies the type with which this message is represented in its decoded state.
   */
  val type: KClass<out DM>

  /**
   * Encodes a message to its wire representation.
   */
  fun encode(alloc: ByteBufAllocator, msg: DM): WM
}
