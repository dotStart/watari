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
package tv.dotstart.watari.protocol.pmp.message.registry

import tv.dotstart.watari.protocol.pmp.message.Message
import tv.dotstart.watari.protocol.pmp.message.reader.MessageReader
import tv.dotstart.watari.protocol.pmp.message.wire.WireMessage
import kotlin.reflect.KClass

/**
 * Provides a basis for registries which associate readers for a set of supported messages.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
interface MessageReaderRegistry<out DM : Message, WM : WireMessage> {

  /**
   * Identifies the wire type to which readers within this message registry encode from.
   */
  val wireType: KClass<out WM>

  /**
   * Retrieves a reader for a given opcode.
   *
   * When no reader is registered for the desired opcode, `null` is returned instead.
   */
  fun readerFor(opcode: UByte): MessageReader<DM, WM>?
}
