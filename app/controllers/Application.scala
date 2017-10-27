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

package controllers

import util._

import play.api._
import play.api.i18n.Lang
import play.api.mvc._
import play.api.Play.current

import System.{currentTimeMillis => now}

object Application extends Controller with Helpers {
  def index = Action { implicit request =>
    Ok(views.html.index(m('welcomeMessage, 'now -> now)))
  }

  def lang(lang: String) = Action { implicit request =>
    RedirectToRefererOrHome.withLang(Lang(lang))
  }
}
