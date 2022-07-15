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

/**
 * Facilitates the discovery of gateway devices within the local network.
 *
 * @author Johannes Donath
 * @date 14/07/2022
 * @since 0.1.0
 */
module watari.discovery.gateway {
  requires kotlin.stdlib;
  requires org.apache.logging.log4j;
  requires watari.common;

  exports tv.dotstart.watari.discovery.gateway;
  exports tv.dotstart.watari.discovery.gateway.registry;

  provides tv.dotstart.watari.discovery.gateway.GatewayDiscoveryService
      with tv.dotstart.watari.discovery.gateway.NetstatGatewayDiscoveryService,
          tv.dotstart.watari.discovery.gateway.GuessingGatewayDiscoveryService;
}
