/*
 * Copyright (C) 2015 Myroslav Rudnytskyi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
package intelligent.wiki.editor.bot.io.wiki

import groovy.transform.PackageScope
import groovy.util.slurpersupport.GPathResult

/**
 * This utility class provide some methods to work with
 * <a href=https://www.mediawiki.org/wiki/API:Query>Wikipedia query</a>).
 * Please, do not use it directly!
 *
 * @author Myroslav Rudnytskyi
 * @version 09.10.2015
 */
@PackageScope
class WikiQuery {

	@PackageScope
	def String getArticleContent(URL request) {
		// return <rev> tag
		parse(request).query.pages.page.revisions.rev
	}

	private GPathResult parse(URL request) {
		new XmlSlurper().parse(request.openStream())
	}

	@PackageScope
	def List<String> getPages(URL request) {
		Collection collection = parse(request).'**'.findAll {
				// find all <p> tags with "title" attribute
			node -> node.name() == 'p'
		}*.@title

		// repack attributes to List<String>
		def list = new ArrayList<String>()
		for (Object object : collection) {
			list.add(object.toString())
		}
		list
	}
}
