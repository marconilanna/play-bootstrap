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

package test.helpers

import helpers._

class assets extends org.scalatest.FunSuite {
  test("CSS: url") {
    val result = css.url("test")
    assert(result === "/css/test.css")
  }

  test("CSS: apply") {
    val result = css("test")
    assert(result.body === """<link href="/css/test.css" rel="stylesheet">""")
  }

  test("CSS: apply media") {
    val result = css("test", "print")
    assert(result.body === """<link href="/css/test.css" media="print" rel="stylesheet">""")
  }

  test("CSS: apply media null") {
    val result = css("test", null)
    assert(result.body === """<link href="/css/test.css" rel="stylesheet">""")
  }

  test("CSS: apply media empty") {
    val result = css("test", "")
    assert(result.body === """<link href="/css/test.css" rel="stylesheet">""")

    val result2 = css("test", " ")
    assert(result2.body === """<link href="/css/test.css" rel="stylesheet">""")

    val result3 = css("test", "\t")
    assert(result3.body === """<link href="/css/test.css" rel="stylesheet">""")

    val result4 = css("test", "  \t")
    assert(result4.body === """<link href="/css/test.css" rel="stylesheet">""")
  }

  test("Images: url") {
    val result = img.url("test.png")
    assert(result === "/img/test.png")
  }

  test("Images: apply") {
    val result = img("test.png")
    assert(result.body === """<img src="/img/test.png">""")
  }

  test("Images: apply alt") {
    val result = img("test.png", "alt test")
    assert(result.body === """<img src="/img/test.png" alt="alt test">""")
  }

  test("Images: apply args") {
    val result = img("test.png", 'class -> "test")
    assert(result.body === """<img src="/img/test.png" class="test">""")
  }

  test("Images: apply alt & args") {
    val result = img("test.png", "alt test", 'class -> "test")
    assert(result.body === """<img src="/img/test.png" alt="alt test" class="test">""")
  }

  test("JavaScript: url") {
    val result = js.url("test")
    assert(result === "/js/test.js")
  }

  test("JavaScript: apply") {
    val result = js("test")
    assert(result.body === """<script src="/js/test.js"></script>""")
  }
}
