/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
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
package intelligent.wiki.editor.io_impl.wiki

import groovy.json.JsonSlurper
import groovy.transform.PackageScope
import groovy.util.slurpersupport.GPathResult
import intelligent.wiki.editor.io_api.TemplateParameter
import intelligent.wiki.editor.io_impl.wiki.template_data.TemplateParameterBuilder

/**
 * This utility class provide some methods to work with
 * <a href=https://www.mediawiki.org/wiki/API:Query>Wikipedia query</a>).
 * Please, do not use it directly - it is not a part of public API!
 *
 * @author Myroslav Rudnytskyi
 * @version 09.10.2015
 */
@PackageScope
class WikiQuery {

	@PackageScope
	static
	def String getArticleContent(URL request) {
		// return <rev> tag
		parseXML(request).query.pages.page.revisions.rev
	}

	@PackageScope
	static
	def List<String> getPages(URL request) {
		Collection pTags = parseXML(request).'**'.findAll {
				// find all <p> tags with "title" attribute
			node -> node.name() == 'p'
		}*.@title

		// repack attributes to List<String>
		def list = new ArrayList<String>()
		for (Object object : pTags) {
			list.add(object.toString())
		}
		list
	}

	@PackageScope
	static
	def List<TemplateParameter> getTemplatesParams(URL request) {
		enableAsMentionedTemplateParametersSortingOrder()
		def list = new ArrayList<TemplateParameter>()
		// safe to NPE
		def params = parseJSON(request).pages?.values()?.getAt(0)?.params
		if (params != null) {
			for (def entry : params.entrySet()) {
				list.add(TemplateParameterBuilder.parameterWithName(entry.getKey())
						.withDescription(entry.getValue().get("description")?.values()?.getAt(0))
						.withDefaultValue(entry.getValue().get("default"))
						.withType(entry.getValue().get("type"))
						.withRequiredFlag(entry.getValue().get("required"))
						.withSuggestedFlag(entry.getValue().get("suggested"))
						.withDeprecatedFlag(entry.getValue().get("deprecated"))
						.build())
			}
		}
		list
	}

	private static GPathResult parseXML(URL request) {
		assert request.toString().contains("https") && request.toString().contains("xml")
		new XmlSlurper().parse(request.openStream())
	}

	private static Object parseJSON(URL request) {
		assert request.toString().contains("https") && request.toString().contains("json")
		new JsonSlurper().parse(request.openStream())
	}

	// see "LazyMap" class "buildIfNeeded" method implementation
	private static void enableAsMentionedTemplateParametersSortingOrder() {
		System.setProperty("jdk.map.althashing.threshold", "512")
	}
}
