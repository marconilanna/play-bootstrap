/*
 * Copyright 2013 Marconi Lanna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package util

import java.io.File

import com.typesafe.config._

/**
 * A very thin wrapper for Typesafe Config's `ConfigFactory`.
 * Loads a '''HOCON''' file without merging it with other configuration,
 * encapsulating the returned `Config` object and providing a more idiomatic
 * Scala interface to it.
 */
private[util] final class Resource(resource: String) {
  private val cfg = ConfigFactory.parseFile(new File("conf/" + resource))
      .resolve(ConfigResolveOptions.noSystem)

  def apply (key: String) = if (has(key)) Some(cfg.getString (key)) else None
  def int   (key: String) = if (has(key)) Some(cfg.getInt    (key)) else None
  def bool  (key: String) = if (has(key)) Some(cfg.getBoolean(key)) else None
  def double(key: String) = if (has(key)) Some(cfg.getDouble (key)) else None
  def has   (key: String) = cfg.hasPath(key)
}

private[util] object Resource {
  def apply(resource: String) = new Resource(resource)
}
