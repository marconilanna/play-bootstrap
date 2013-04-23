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

package test.integration

import play.api.test._
import play.api.test.Helpers._

class Landing extends org.scalatest.FunSuite {
	test("Landing page") {
		running(TestServer(3333), HTMLUNIT) { browser =>
			browser.goTo("http://localhost:3333/")
			assert(browser.title.contains("Home"))
			assert(browser.pageSource.contains("Hello"))
		}
	}

	test("Language selection") {
		running(TestServer(3333), HTMLUNIT) { browser =>
			browser.goTo("http://localhost:3333/lang/pt-BR")
			assert(browser.title.contains("Página Principal"))
			assert(browser.pageSource.contains("Oi! Hoje é"))

			browser.goTo("http://localhost:3333/lang/en-US")
			assert(browser.title.contains("Home"))
			assert(browser.pageSource.contains("Hi! Today is"))
		}
	}
}
