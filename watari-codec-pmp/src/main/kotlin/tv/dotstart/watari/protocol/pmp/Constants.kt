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
package tv.dotstart.watari.protocol.pmp

/**
 * Defines various constants within the PMP protocol.
 *
 * @author Johannes Donath
 * @date 23/07/2022
 * @since 0.1.0
 */

/**
 * Defines the port on which clients shall listen in order to retrieve broadcasts from a PMP server.
 *
 * This port only applies when clients desire to receive eagerly broadcasted notifications from a
 * PMP server within the network. Otherwise, clients may choose an arbitrary port so long as it is
 * different from [SERVER_PORT].
 */
const val CLIENT_PORT = 5350

/**
 * Defines the port on which servers shall listen on to serve client requests.
 */
const val SERVER_PORT = 5351

/**
 * Defines the multicast address to which PMP servers shall transmit eager broadcasts.
 */
const val MULTICAST_ADDRESS = "224.0.0.1"
