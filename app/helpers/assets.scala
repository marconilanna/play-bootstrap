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

package helpers

import play.api.templates.{Html, PlayMagic}

/**
 * Static resources convenience handlers
 *
 */
private[helpers] sealed trait asset {
  protected val ext = "." + folder
  protected def folder: String

  protected def toArgs(args: (Symbol, Any)*) = PlayMagic.toHtmlArgs(args.toMap).body

  protected def opt(t: String, r: => String) = if (t == null || t.trim == "") "" else r

  def url(resource: String) = controllers.routes.Assets.at(folder + "/" + resource + ext).url
}

object css extends asset {
  protected def folder = "css"

  def apply(resource: String, media: String="") = {
    val m = opt(media, s""" media="$media"""")
    Html(s"""<link href="${url(resource)}"$m rel="stylesheet">""")
  }
}

object img extends asset {
  override protected val ext = ""
  protected def folder = "img"

  def apply(resource: String, args: (Symbol, Any)*): Html = apply(resource, "", args:_*)

  def apply(resource: String, alt: String, args: (Symbol, Any)*) = {
    val a = opt(alt, s""" alt="$alt"""")
    val t = toArgs(args:_*)
    val r = opt(t, " " + t)
    Html(s"""<img src="${url(resource)}"$a$r>""")
  }
}

object js extends asset {
  protected def folder = "js"

  def apply(resource: String) = Html(s"""<script src="${url(resource)}"></script>""")
}
