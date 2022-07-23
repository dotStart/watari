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
import tv.dotstart.watari.protocol.pmp.message.writer.MessageWriter
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * Provides an abstract basis for codec registry implementations.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
abstract class AbstractMessageCodecRegistry<DM : Message, WM : WireMessage>(
  readers: List<MessageReader<DM, WM>>,
  writers: List<MessageWriter<out DM, WM>>
) : MessageCodecRegistry<DM, WM> {

  private val readers = readers
    .map { it.opcode to it }
    .toMap()

  private val writers = writers
    .map { it.type to it }
    .toMap()

  override fun readerFor(opcode: UByte) = this.readers[opcode]

  @Suppress("UNCHECKED_CAST")
  override fun <T : DM> writerFor(type: KClass<out T>): MessageWriter<T, WM>? = (this.writers[type]
    ?: this.writers.values // locate a compatible writer that isn't an exact match
      .find { type.isSubclassOf(it.type) })
      as MessageWriter<T, WM>?
}
