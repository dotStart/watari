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
 * Provides netty codecs for en- and decoding PMP and PCP messages.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
module watari.codec.pmp {
  requires io.netty.buffer;
  requires io.netty.codec;
  requires io.netty.common;
  requires io.netty.transport;
  requires kotlin.reflect;
  requires kotlin.stdlib;
  requires org.apache.logging.log4j;
  requires watari.common;

  exports tv.dotstart.watari.protocol.pmp;
  exports tv.dotstart.watari.protocol.pmp.codec;
  exports tv.dotstart.watari.protocol.pmp.message;
  exports tv.dotstart.watari.protocol.pmp.message.reader;
  exports tv.dotstart.watari.protocol.pmp.message.registry;
  exports tv.dotstart.watari.protocol.pmp.message.request;
  exports tv.dotstart.watari.protocol.pmp.message.request.reader;
  exports tv.dotstart.watari.protocol.pmp.message.request.v0;
  exports tv.dotstart.watari.protocol.pmp.message.request.v0.reader;
  exports tv.dotstart.watari.protocol.pmp.message.request.v0.writer;
  exports tv.dotstart.watari.protocol.pmp.message.request.writer;
  exports tv.dotstart.watari.protocol.pmp.message.response;
  exports tv.dotstart.watari.protocol.pmp.message.response.reader;
  exports tv.dotstart.watari.protocol.pmp.message.response.v0;
  exports tv.dotstart.watari.protocol.pmp.message.response.v0.reader;
  exports tv.dotstart.watari.protocol.pmp.message.response.v0.writer;
  exports tv.dotstart.watari.protocol.pmp.message.response.writer;
  exports tv.dotstart.watari.protocol.pmp.message.wire;
  exports tv.dotstart.watari.protocol.pmp.message.writer;
}
