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

import play.api.mvc._

import java.net.URL

private[controllers] trait Helpers { this: Controller =>
	private val home = routes.Application.index.url

	def RedirectToRefererOrHome(implicit request: Request[_]) = Redirect {
		request.headers.get("Referer").fold(home){ url =>
			try {
				new URL(url).getFile
			} catch {
				case e: Exception => home
			}
		}
	}
}
