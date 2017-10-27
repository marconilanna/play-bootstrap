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

package models.persistence

/**
 * Supertrait for Active Record traits.
 * Implements the Active Record pattern as originally described by Martin Fowler in his PEAA book.
 * All database interactions are actually delegated to the `Dao` data access trait.
 */
private[persistence] trait ActiveRecord[M <: ActiveRecord[M]] { this: M =>
  val id: PrimaryKey

  protected def dao: Dao[M]

  private[persistence] def withId(id: PrimaryKey): M

  private[persistence] lazy val toSeq = getClass.getDeclaredFields.toSeq map { f =>
    f.setAccessible(true)
    (Symbol(f.getName), f.get(this))
  }

  def save() = dao.save(this)
}
