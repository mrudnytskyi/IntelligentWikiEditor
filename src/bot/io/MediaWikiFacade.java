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
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import utils.MutableString;

/**
 * Class, created to get access to data, stored on <a
 * href=http://https://www.mediawiki.org/wiki/API:Main_page>MediaWiki</a>
 * servers, such as Wikipedia articles, templates, categories and so on. Note,
 * that all it's methods can throw {@link IOException}.
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
		UKRAINIAN("uk"), ENGLISH("en"), GERMAN("ge"), RUSSIAN("ru");

		private final String code;

		private Language(String code) {
			this.code = code;
		}

		/**
		 * Returns language code for this enum constant in {@link String}
		 * representation.
		 * 
		 * @return language code for this enum constant
		 */
		@Override
		public String toString() {
			return code;
		}
	}

	/**
	 * Enumeration, containing Mediawiki namespace constants, such as "0" stands
	 * for Main or article namespace, "10" for templates namespace (starting
	 * with "Template:" preffix).
	 * 
	 * @author Myroslav Rudnytskyi
	 * @version 0.1 23.04.2015
	 */
	private enum WikiNamespace {
		ARTICLE(0), MAIN(0), FILE(6), TEMPLATE(10), CATEGORY(14);

		private final int code;

		private WikiNamespace(int code) {
			this.code = code;
		}

		/**
		 * Returns namespace constants in {@link String} representation.
		 * 
		 * @return namespace constant for this enum constant
		 */
		@Override
		public String toString() {
			return String.valueOf(code);
		}
	}

	private static String WIKIPEDIA_API = "http://uk.wikipedia.org/w/api.php";

	// TODO: will be used later, in getFiles method
	@SuppressWarnings("unused")
	private static String COMMONS_API = "http://commons.wikimedia.org/w/api.php";

	private static String QUERY_LINK = "?action=query";

	/**
	 * do not instantiate this class
	 */
	private MediaWikiFacade() {
	}

	/**
	 * Sets specified language. Note, that Ukrainian is used by default.
	 * 
	 * @param lang
	 *            language to set
	 */
	public static void setLanguage(Language lang) {
		MutableString ms = new MutableString("http://", lang.toString(),
				".wikipedia.org/w/api.php");
		WIKIPEDIA_API = ms.toString();
	}

	/**
	 * Checks if {@link String} arguments are not null or empty. Note, that
	 * every method in this class <strong>must</strong> make this check,
	 * otherwise method results can be unexpected ("null" is correct String
	 * value) or even harmful for Web servers ("" stands for all pages). 
	 * 
	 * @param args
	 *            parameters to check
	 */
	private static void checkArguments(String... args) {
		for (String s : args) {
			if (s == null || s.isEmpty()) {
				throw new IllegalArgumentException(
						"Parameter can not be null or empty!");
			}
		}
	}

	/**
	 * Gets plain wiki text of specified article. Note, that article name
	 * depends on chosen language. See {@link #setLanguage(Language)
	 * setLanguage()} method for details.
	 * 
	 * @param name
	 *            full article name in chosen language
	 * @return wiki text in {@link String} representation
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String getArticleText(String name) throws IOException {
		checkArguments(name);
		String text = null;
		URL url = new URL(
				new MutableString(WIKIPEDIA_API, QUERY_LINK,
						"&format=xml&prop=revisions&titles=", name,
						"&rvprop=content").toString());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			Document doc = factory.newDocumentBuilder().parse(url.openStream());
			XPath xpath = XPathFactory.newInstance().newXPath();
			String allPagesPath = "/api/query/pages/page/revisions/rev";
			Node result = (Node) xpath.compile(allPagesPath).evaluate(doc,
					XPathConstants.NODE);
			if (result != null) {
				text = result.getFirstChild().getNodeValue().toString();
			}
		} catch (ParserConfigurationException | XPathExpressionException
				| SAXException e) {
			// can not catch smth, but if yes, rethrow as checked exception
			throw new RuntimeException(e);
		}
		return text;
	}

	/**
	 * Gets list of all pages from specified namespace, starting with specified
	 * preffix.
	 * 
	 * @param preffix
	 *            preffix
	 * @param continueFrom
	 *            page name, on which query was stopped, if result has more than
	 *            500 occurrences
	 * @param namespace
	 *            {@link WikiNamespace namespace} object
	 * @return list of pages, starting with specified prefix
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	private static List<String> getPagesStartingWith(String preffix,
			String continueFrom, WikiNamespace namespace) throws IOException {

		checkArguments(preffix);
		List<String> pages = new ArrayList<String>();
		URL url = new URL(new MutableString(WIKIPEDIA_API, QUERY_LINK,
				"&list=allpages&format=xml&apprefix=", preffix,
				"&apnamespace=", namespace.toString(), "&aplimit=500&apfrom=",
				continueFrom != null ? continueFrom : "").toString());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			Document doc = factory.newDocumentBuilder().parse(url.openStream());
			XPath xpath = XPathFactory.newInstance().newXPath();
			String allPagesPath = "/api/query/allpages";
			Node allPages = (Node) xpath.compile(allPagesPath).evaluate(doc,
					XPathConstants.NODE);
			for (int i = 0; i < allPages.getChildNodes().getLength(); i++) {
				pages.add(allPages.getChildNodes().item(i).getAttributes()
						.getNamedItem("title").getNodeValue());
			}

			String continueAllPagesPath = "/api/query-continue/allpages";
			Node continueAllPages = (Node) xpath.compile(continueAllPagesPath)
					.evaluate(doc, XPathConstants.NODE);
			if (continueAllPages != null) {
				String nextContinueFrom = continueAllPages.getAttributes()
						.getNamedItem("apcontinue").getNodeValue();
				pages.addAll(getPagesStartingWith(preffix, nextContinueFrom,
						namespace));
			}
		} catch (ParserConfigurationException | XPathExpressionException
				| SAXException e) {
			// can not catch smth, but if yes, rethrow as checked exception
			throw new RuntimeException(e);
		}
		return pages;
	}

	/**
	 * Gets array of templates names, starting with specified preffix.
	 * 
	 * @param preffix
	 *            preffix
	 * @return array of pages, starting with specified prefix
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String[] getTemplatesStartingWith(String preffix)
			throws IOException {

		List<String> result = getPagesStartingWith(preffix, null,
				WikiNamespace.TEMPLATE);
		return result.toArray(new String[result.size()]);
	}

	/**
	 * Gets array of categories names, starting with specified prefix.
	 * 
	 * @param preffix
	 * @return list of pages, starting with specified prefix
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String[] getCategoriesStartingWith(String preffix)
			throws IOException {

		List<String> result = getPagesStartingWith(preffix, null,
				WikiNamespace.CATEGORY);
		return result.toArray(new String[result.size()]);
	}
}