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

package test.views.html.layout

import views.html.layout

class base extends org.scalatest.FunSuite {
	test("Title: no parameters") {
		val result = layout.base()(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title>Site Name</title>"))
	}

	test("Title: null parameter") {
		val result = layout.base(null)(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title>Site Name</title>"))
	}

	test("Title: empty string") {
		val result = layout.base("")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title>Site Name</title>"))
	}

	test("Title: simple string") {
		val result = layout.base("Test")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title>Test - Site Name</title>"))
	}

	test("Title: Unicode string") {
		val result = layout.base("àêíõüç ÀÊÍÕÜÇ π Я 中文 漢字 한글 ☃")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title>àêíõüç ÀÊÍÕÜÇ π Я 中文 漢字 한글 ☃ - Site Name</title>"))
	}

	test("Title: HTML encoding") {
		val result = layout.base("\"Test\"</title>")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title>&quot;Test&quot;&lt;/title&gt; - Site Name</title>"))
	}

	test("Site name: null parameter") {
		val result = layout.base(siteName = null)(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title></title>"))
	}

	test("Site name: empty string") {
		val result = layout.base(siteName = "")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title></title>"))
	}

	test("Site name: simple string") {
		val result = layout.base(siteName = "Test")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<title>Test</title>"))
	}

	test("Site name and title: simple string") {
		val result = layout.base("My page", "MySite.com")(null)
				assert(result.contentType === "text/html")
				assert(result.body.contains("<title>My page - MySite.com</title>"))
	}

	test("Description: null parameter") {
		val result = layout.base(description = null)(null)
				assert(result.contentType === "text/html")
				assert(!result.body.contains("<meta name=\"description\""))
	}

	test("Description: empty string") {
		val result = layout.base(description = "")(null)
				assert(result.contentType === "text/html")
				assert(!result.body.contains("<meta name=\"description\""))
	}

	test("Description: simple string") {
		val result = layout.base(description = "Test")(null)
				assert(result.contentType === "text/html")
				assert(result.body.contains("<meta name=\"description\" content=\"Test\">"))
	}

	test("Body: no id, no class") {
		val result = layout.base()(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<body>"))
	}

	test("Body: id only") {
		val result = layout.base(id = "testId")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<body id=\"testId\">"))
	}

	test("Body: class only") {
		val result = layout.base(klass = "testClass")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<body class=\"testClass\">"))
	}

	test("Body: id and class") {
		val result = layout.base(id = "testId", klass = "testClass")(null)
		assert(result.contentType === "text/html")
		assert(result.body.contains("<body id=\"testId\" class=\"testClass\">"))
	}
}
