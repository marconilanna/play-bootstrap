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

package util

import play.api.i18n.Lang
import play.api.Logger

import scala.collection.JavaConverters._
import scala.collection.mutable

import com.ibm.icu.text.MessageFormat

object m {
  private val messagesCache = mutable.Map[Lang, Resource]()
  private def messages(lang: Lang) =
      messagesCache.getOrElseUpdate(lang, Resource("i18n/messages." + lang.code + ".conf"))

  private val formatCache = mutable.Map[(String, Lang), MessageFormat]()
  private def format(key: String)(implicit lang: Lang) =
      formatCache.getOrElseUpdate((key, lang), new MessageFormat(m(key), lang.toLocale))

  def apply(key: String)(implicit lang: Lang): String = messages(lang)(key).getOrElse {
    Logger.warn(s"Invalid i18n key '$key', locale '${lang.code}'")
    key
  }

  def apply(key: String, args: (String, Any)*)(implicit lang: Lang): String =
      format(key).format(args.toMap.asJava)
}
