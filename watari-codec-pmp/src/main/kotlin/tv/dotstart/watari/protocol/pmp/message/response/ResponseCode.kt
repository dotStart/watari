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

/**
 * Provides a listing of recognized response codes.
 *
 * @author Johannes Donath
 * @date 08/07/2022
 */
enum class ResponseCode(

  /**
   * Identifies the raw response code which is transmitted on the wire to identify this code.
   */
  val code: UShort,

  /**
   * Identifies whether this response code is considered a transient error (e.g. may resolve itself
   * after a while if the operation is retried with the same set of arguments).
   */
  val transient: Boolean = false
) {

  /**
   * Unknown
   * -------
   *
   * Identifies a response code which is not currently recognized by this library implementation and
   * is likely not part of any of the supported specifications.
   *
   * This value is currently set to 255 (the highest permitted value) and is unlikely to be used by
   * the specification in the near future.
   */
  UNKNOWN(UShort.MAX_VALUE),

  /**
   * Success
   * -------
   *
   * The given operation has successfully carried out and is now visible to the outer network.
   */
  SUCCESS(0u),

  /**
   * Unsupported Version
   * -------------------
   *
   * The desired version of the protocol mapping protocol is not recognized by the responding
   * gateway. No operation has been carried out.
   */
  UNSUPPORTED_VERSION(1u),

  /**
   * Not Authorized
   * --------------
   *
   * The desired operation is not permitted by the gateway for the executing device. This likely
   * indicates that port mapping is supported by the gateway but has been explicitly disabled by an
   * administrator for either the entire network, a network subsection or the executing device.
   */
  NOT_AUTHORIZED(2u),

  /**
   * Network Failure
   * ---------------
   *
   * The desired operation cannot be carried out due to an upstream network failure. This likely
   * indicates that the gateway has not acquired an address lease on its upstream connection yet and
   * is thus unable to serve the request.
   */
  NETWORK_FAILURE(3u, true),

  /**
   * Out of Resources
   * ----------------
   *
   * The desired operation cannot be carried out due to resource limitations on the gateway. This
   * likely indicates that there is too many active mappings on the gateway at the moment.
   */
  OUT_OF_RESOURCES(4u, true),

  /**
   * Unsupported Operation
   * ---------------------
   *
   * The desired operation is not currently supported by the gateway. This likely indicates that the
   * gateway implementation is non-compliant.
   */
  UNSUPPORTED_OPERATION(5u);

  companion object {

    /**
     * Retrieves a response code value based on its encoded representation.
     *
     * When a given code is not defined within this specification, [UNKNOWN] is returned as a
     * fallback value instead.
     */
    fun byCode(code: UShort): ResponseCode =
      values()
        .find { it.code == code }
        ?: UNKNOWN
  }
}
