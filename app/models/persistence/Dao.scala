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

import util._

import anorm._
import play.api.db.DB
import play.api.Logger
import play.api.Play.current

import scala.language.implicitConversions

import java.sql.SQLException

/**
 * A data access trait providing a thin, concise (less verbose) abstraction layer on top of Anorm.
 * SQL queries are loaded from external files, parsed, and cached.
 * The `Dao` trait is the only code to interact with Anorm.
 */
private[persistence] trait Dao[M <: ActiveRecord[M]] {
	protected type Row = SqlRow

	protected def table: String
	protected def parse(row: Row): M

	private lazy val queries = Query(table)
	private lazy val queryCache = collection.mutable.Map[Symbol, SqlQuery]()
	private def query(q: Symbol) = queryCache.getOrElseUpdate(q, SQL(queries(q)))

	/* A shim for Anorm's implicitly converted parameters */ 
	private implicit def toAnormParameterValue(s: Seq[(Symbol, Any)]) = s.map(_ match {
		case (p, v: Id[_]) => (p, toParameterValue(v.get))
		case (p, v)        => (p, toParameterValue(v))
	})

	private implicit def toId(id: Long) = Id(id)

	private def error(e: SQLException, q: Symbol, on: (Symbol, Any)*) {
		Logger.error(s"Error executing query '$table:${q.name}' with parameters:\n" +
				on.map(p => s"\t${p._1.name}: '${p._2}'").mkString("\n"), e)
	}

	protected def prefix(s: String) =       s + "%"
	protected def infix (s: String) = "%" + s + "%"
	protected def suffix(s: String) = "%" + s

	protected def insert(q: Symbol, on: (Symbol, Any)*): Option[Long] = DB.withConnection {
		implicit c =>
		try {
			query(q).on(on:_*).executeInsert()
		} catch {
			case e: SQLException =>
				error(e, q, on:_*)
				None
		}
	}

	protected def update(q: Symbol, on: (Symbol, Any)*): Int = DB.withConnection {
		implicit c =>
		try {
			query(q).on(on:_*).executeUpdate()
		} catch {
			case e: SQLException =>
				error(e, q, on:_*)
				0
		}
	}

	protected def select(q: Symbol, on: (Symbol, Any)*): List[Row] = DB.withConnection {
		implicit c =>
		try {
			query(q).on(on:_*)().toList
		} catch {
			case e: SQLException =>
				error(e, q, on:_*)
				Nil
		}
	}

	protected def selectMany(q: Symbol, on: (Symbol, Any)*): List[M] =
			select(q, on:_*) map {parse _}

	protected def selectOne(q: Symbol, on: (Symbol, Any)*): Option[M] =
			selectMany(q, on:_*).headOption

	def apply(id: Long) = selectOne('get, 'id -> id)

	def apply(instance: M): Option[M] = instance.id match {
		case NotAssigned => None
		case Id(_) => apply(instance.id.get)
	}

	def save(instance: M): Option[M] = instance.id match {
		case NotAssigned => insert('insert, instance.toSeq:_*).map(instance.withId(_))
		case Id(_) => if (update('update, instance.toSeq:_*) > 0) Some(instance)
		              else None
	}
}
