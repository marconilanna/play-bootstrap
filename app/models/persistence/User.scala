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

import models.User

import java.util.Date

private[models] trait UserActiveRecord extends ActiveRecord[User] { this: User =>
	protected def dao = User

	private[persistence] def withId(id: PrimaryKey) = copy(id = id)
}

private[models] trait UserDao extends Dao[User] { this: User.type =>
	protected def table = "user"

	protected def parse(row: Row) = User(
		  row[PrimaryKey]("id")
		, row[String]("name")
		, row[String]("legal_name")
		, row[String]("email")
		, row[String]("password")
		, row[String]("status")
		, row[Date]("created")
		, row[Option[Date]]("first_login")
		, row[Option[Date]]("last_login")
		, row[Option[Date]]("password_changed")
		, row[Int]("failed_attempts")
	)

	def byName(name: String) = selectMany('byName, 'name -> name)

	def byNameLike(name: String) = selectMany('byNameLike, 'name -> infix(name))

	def byLegalName(legalName: String) = selectMany('byLegalName, 'legalName -> legalName)

	def byLegalNameLike(legalName: String) = selectMany('byLegalNameLike
			, 'legalName -> infix(legalName))

	def byEmail(email: String) = selectOne('byEmail, 'email -> email)
}
