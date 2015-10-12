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

package intelligent.wiki.editor.bot.io.wiki;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This utility class provide some methods to work with
 * <a href=https://www.mediawiki.org/wiki/API:Query>Wikipedia query</a>).
 * Please, do not use it directly!
 *
 * @author Myroslav Rudnytskyi
 * @version 10.10.2015
 */
public class WikiQueryJ {

	private DocumentBuilder builder;

	private XPathExpression pagesPath;

	private XPathExpression articleContentPath;

	WikiQueryJ() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		XPath xpath = XPathFactory.newInstance().newXPath();
		try {
			pagesPath = xpath.compile("/api/query/allpages");
			articleContentPath = xpath.compile("/api/query/pages/page/revisions/rev");
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}

	List<String> getPages(URL request) throws IOException {
		List<String> pages = new ArrayList<>();
		try {
			Document doc = builder.parse(request.openStream());
			Node allPages = (Node) pagesPath.evaluate(doc, XPathConstants.NODE);
			NodeList nodes = allPages.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				String pageTitle = nodes.item(i).getAttributes().getNamedItem("title").getNodeValue();
				pages.add(pageTitle);
			}
		} catch (XPathExpressionException | SAXException e) {
			// can not catch smth, but if yes, rethrow as checked exception
			throw new RuntimeException(e);
		}
		return pages;
	}

	String getArticleContent(URL request) throws IOException {
		String text = "";
		try {
			Document doc = builder.parse(request.openStream());
			Node result = (Node) articleContentPath.evaluate(doc, XPathConstants.NODE);
			if (result != null) {
				text = result.getFirstChild().getNodeValue();
			}
		} catch (XPathExpressionException | SAXException e) {
			// can not catch smth, but if yes, rethrow as checked exception
			throw new RuntimeException(e);
		}
		return text;
	}
}
