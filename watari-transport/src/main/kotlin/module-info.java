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
 * Abstracts access to the various network transports available within the netty runtime.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
module watari.transport {
  requires kotlin.stdlib;
  requires watari.common;

  requires io.netty.transport;
  requires io.netty.transport.unix.common;

  requires io.netty.transport.classes.epoll;
  requires io.netty.transport.classes.kqueue;
  requires io.netty.transport.epoll.linux.aarch_64;
  requires io.netty.transport.epoll.linux.x86_64;
  requires io.netty.transport.kqueue.osx.aarch_64;
  requires io.netty.transport.kqueue.osx.x86_64;

  exports tv.dotstart.watari.transport;
  exports tv.dotstart.watari.transport.registry;

  provides tv.dotstart.watari.transport.Transport with
      tv.dotstart.watari.transport.EpollTransport,
      tv.dotstart.watari.transport.KQueueTransport,
      tv.dotstart.watari.transport.NioTransport;
}
