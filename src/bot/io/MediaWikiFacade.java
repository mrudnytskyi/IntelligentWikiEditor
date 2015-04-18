/*
 * MediaWikiFacade.java	27.10.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
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
package bot.io;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import utils.MutableString;

/**
 * Class, created to get access to Wikipedia data, such as articles, templates,
 * categories and so on. Note, that all it's methods can throw 
 * {@link IOException}.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 27.10.2014
 */
public final class MediaWikiFacade {
	
	/**
	 * Enumeration, containing language codes, such as "uk" stands for
	 * Ukrainian, "en" for English and so on.
	 * 
	 * @author Mir4ik
	 * @version 0.1 27.10.2014
	 */
	public enum Language {
		UKRAINIAN("uk"),
		ENGLISH("en"),
		GERMAN("ge"),
		RUSSIAN("ru");
		
		private final String code;
		
		private Language(String code) {
			this.code = code;
		}
		
		/**
		 * Returns language code for this enum constant in {@link String}
		 * representation.
		 * 
		 * @return	language code for this enum constant
		 */
		@Override
		public String toString() {
			return code;
		}
	}

	private static String QUERY_STRING = 
			"http://uk.wikipedia.org/w/api.php?action=query";
	
	/**
	 * do not instantiate this class
	 */
	private MediaWikiFacade() {}
	
	/**
	 * Sets specified language. Note, that Ukrainian is used by default.
	 * 
	 * @param lang	language to set
	 */
	public static void setLanguage(Language lang) {
		MutableString ms = new MutableString("http://", lang.toString(),
				".wikipedia.org/w/api.php?action=query");
		QUERY_STRING = ms.toString();
	}

	/**
	 * Gets plain wiki text of specified article. Note, that article name
	 * depends on chosen language. See {@link #setLanguage(Language) 
	 * setLanguage()} method for details.
	 * 
	 * @param name			full article name in chosen language
	 * @return				wiki text in {@link String} representation
	 * @throws IOException	if an I/O error occurs
	 */
	public static String getArticleText(String name) throws IOException {
		String text = null;
		URL url = new URL(new MutableString(QUERY_STRING,
				"&format=xml&prop=revisions&titles=", name,
				"&rvprop=content").toString());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(url.openStream());
			XPathFactory pathFactory = XPathFactory.newInstance();
			XPath xpath = pathFactory.newXPath();
			// get "rev" XML element
			XPathExpression expr = xpath.compile("//rev");
			Object result = expr.evaluate(doc, XPathConstants.NODE);
			Node node = (Node) result;
			text = node.getFirstChild().getNodeValue().toString();
		} catch (ParserConfigurationException | XPathExpressionException |
				SAXException e) {
			// can not catch smth, but if yes, rethrow as checked exception
			throw new RuntimeException(e);
		}
		return text;
	}
}