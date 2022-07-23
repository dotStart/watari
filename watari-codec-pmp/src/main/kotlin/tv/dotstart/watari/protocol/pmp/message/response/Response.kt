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
package tv.dotstart.watari.protocol.pmp.message.response

import tv.dotstart.watari.protocol.pmp.message.Message

/**
 * Represents a decoded protocol mapping protocol response.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
interface Response : Message {

  /**
   * Stores a response code as returned by the gateway.
   */
  val code: ResponseCode

  /**
   * Identifies the total number of seconds which have passed since the gateway has started.
   */
  val secondsSinceEpoch: UInt

  /**
   * Identifies whether this message indicates a successfully completed operation.
   */
  val isSuccess: Boolean
    get() = this.code == ResponseCode.SUCCESS

  /**
   * Identifies whether this message indicates a failed operation.
   */
  val isError: Boolean
    get() = !this.isSuccess

  companion object {

    /**
     * Identifies the base value for response opcodes.
     */
    internal const val BASE_OPCODE: UByte = 128u
  }
}
