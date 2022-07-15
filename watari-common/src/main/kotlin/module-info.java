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
 * Provides commonly needed functions throughout the library.
 *
 * The contents of this module are considered internal and should not be relied upon outside of the
 * library. No compatibility guarantees are given for the contents of this module.
 *
 * @author Johannes Donath
 * @date 10/07/2022
 * @since 0.1.0
 */
module watari.common {
  requires kotlin.stdlib;

  exports tv.dotstart.watari.common;
  exports tv.dotstart.watari.common.lazy;
  exports tv.dotstart.watari.common.service;
  exports tv.dotstart.watari.common.service.registry;
}
