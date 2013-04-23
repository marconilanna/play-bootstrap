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

package test.util

import util._

import play.api.i18n.Lang

import java.util.GregorianCalendar

class i18n extends org.scalatest.FunSuite {
	test("i18n: simple message") {
		assert(m('home) === "Home")
		assert(m('home)(Lang("en")) === "Home")
		assert(m('home)(Lang("en-US")) === "Home")
		assert(m('home)(Lang("en-CA")) === "Home")
		assert(m('home)(Lang("pt")) === "Página Principal")
		assert(m('home)(Lang("pt-BR")) === "Página Principal")

		assert(m('sitename) === "Site Name")
		assert(m('sitename)(Lang("en")) === "Site Name")
		assert(m('sitename)(Lang("en-US")) === "Site Name")
		assert(m('sitename)(Lang("en-CA")) === "Site Name")
		assert(m('sitename)(Lang("pt")) === "Site Name")
		assert(m('sitename)(Lang("pt-BR")) === "Site Name")
	}

	test("i18n: arguments") {
		val date = new GregorianCalendar(2001, 1, 1, 12, 34, 56).getTime

		assert(m('welcomeMessage, 'now -> date) ===
				"Hi! Today is Thursday, February 1, 2001 and now is 12:34 PM.")

		assert(m('welcomeMessage, 'now -> date)(Lang("en")) ===
				"Hello! Today is Thursday, February 1, 2001 and now is 12:34 PM.")

		assert(m('welcomeMessage, 'now -> date)(Lang("en-US")) ===
				"Hi! Today is Thursday, February 1, 2001 and now is 12:34 PM.")

		assert(m('welcomeMessage, 'now -> date)(Lang("en-CA")) ===
				"Hello, eh? Today is Thursday, February 1, 2001 and now is 12:34 PM.")

		assert(m('welcomeMessage, 'now -> date)(Lang("pt")) ===
				"Olá! Hoje é quinta-feira, 1 de fevereiro de 2001 e agora são 12:34.")

		assert(m('welcomeMessage, 'now -> date)(Lang("pt-BR")) ===
				"Oi! Hoje é quinta-feira, 1 de fevereiro de 2001 e agora são 12:34.")
	}
}
