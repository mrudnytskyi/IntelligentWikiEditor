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
package intelligent.wiki.editor.io_impl.wiki;

import intelligent.wiki.editor.io_api.wiki.Language;
import intelligent.wiki.editor.io_api.wiki.WikiNamespace;
import intelligent.wiki.editor.io_api.wiki.WikiOperations;
import intelligent.wiki.editor.io_api.wiki.template_data.TemplateParameter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Class created to access Wikipedia data using it's API and it is default
 * implementation of {@link WikiOperations}. Note, that it's methods can
 * throw {@link IOException} and their results depend on current language.
 *
 * @author Myroslav Rudnytskyi
 * @version 01.10.2015
 */
public class WikiFacade implements WikiOperations {

	public static final int BOT_QUERY_MAX_COUNT = 500;
	private static final Logger LOG = Logger.getLogger(WikiFacade.class.getName());

	private final Language currentLanguage;

	private final String apiUrl;
	private final String queryUrl = "?action=query";
	private final String templateDataUrl = "?action=templatedata";
	private final String formatXml = "&format=xml";
	private final String formatJson = "&format=json";

	/**
	 * Constructor for creating wiki facade object, using specified language.
	 * Note, that if parameter is <code>null</code>, than
	 * {@link Locale#getDefault() default locale} will be used.
	 *
	 * @param locale locale object, containing specified language
	 */
	public WikiFacade(Locale locale) {
		currentLanguage = Language.createLanguage(locale == null ? Locale.getDefault() : locale);
		// https is necessary!
		apiUrl = "https://" + currentLanguage.getCode() + ".wikipedia.org/w/api.php";
	}

	/**
	 * {@inheritDoc}
	 * Note, that if parameter is <code>null</code> or empty, <code>false</code> will be returned.
	 */
	@Override
	public boolean existsArticle(String name) throws IOException {
		if (isInvalidString(name, "existsArticle")) return false;

		return !getArticleContent(name).isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * Note, that if first parameter is <code>null</code> or empty or
	 * if second parameter is less and equal 0 or more than 500, empty list will be returned.
	 */
	@Override
	public List<String> getArticlesStartingWith(String prefix, int limit) throws IOException {
		if (isNullString(prefix, "getArticlesStartingWith") ||
				isInvalidNumber(limit, "getArticlesStartingWith")) return Collections.emptyList();

		return getPagesStartingWith(prefix, limit, WikiNamespace.MAIN);
	}

	/**
	 * {@inheritDoc}
	 * Note, that if parameter is <code>null</code> or empty, empty string will be returned.
	 */
	@Override
	public String getArticleContent(String name) throws IOException {
		if (isInvalidString(name, "getArticleContent")) return "";

		URL request = new URL(apiUrl + queryUrl + formatXml + "&prop=revisions&rvprop=content&titles=" + normalize(name));
		return WikiQuery.getArticleContent(request);
	}

	/**
	 * {@inheritDoc}
	 * Note, that if parameter is <code>null</code> or empty, <code>false</code> will be returned.
	 * It is no matter if parameter will contain {@link #getCategoryNamespacePrefix() category prefix} or not.
	 */
	@Override
	public boolean existsCategory(String name) throws IOException {
		if (isInvalidString(name, "existsCategory")) return false;

		String prefix = getCategoryNamespacePrefix();
		String categoryName = removePrefixIfExists(name, prefix);

		List<String> startingWith = getCategoriesStartingWith(categoryName, 1);
		return !startingWith.isEmpty() && startingWith.get(0).equals(prefix + categoryName);
	}

	/**
	 * {@inheritDoc}
	 * Note, that if first parameter is <code>null</code> or empty or
	 * if second parameter is less and equal 0 or more than 500, empty list will be returned.
	 * It is no matter if parameter will contain {@link #getCategoryNamespacePrefix() category prefix} or not.
	 */
	@Override
	public List<String> getCategoriesStartingWith(String prefix, int limit) throws IOException {
		if (isNullString(prefix, "getCategoriesStartingWith") ||
				isInvalidNumber(limit, "getCategoriesStartingWith")) return Collections.emptyList();

		String categoryPrefix = removePrefixIfExists(prefix, getCategoryNamespacePrefix());
		return getPagesStartingWith(categoryPrefix, limit, WikiNamespace.CATEGORY);
	}

	@Override
	public String getCategoryNamespacePrefix() {
		return currentLanguage.getCategoryPrefix();
	}

	/**
	 * {@inheritDoc}
	 * Note, that if parameter is <code>null</code> or empty, <code>false</code> will be returned.
	 * It is no matter if parameter will contain {@link #getTemplateNamespacePrefix() template prefix} or not.
	 */
	@Override
	public boolean existsTemplate(String name) throws IOException {
		if (isInvalidString(name, "existsTemplate")) return false;

		String prefix = getTemplateNamespacePrefix();
		String templateName = removePrefixIfExists(name, prefix);

		List<String> startingWith = getTemplatesStartingWith(templateName, 1);
		return !startingWith.isEmpty() && startingWith.get(0).equals(prefix + templateName);
	}

	/**
	 * {@inheritDoc}
	 * Note, that if first parameter is <code>null</code> or empty or
	 * if second parameter is less and equal 0 or more than 500, empty list will be returned.
	 * It is no matter if parameter will contain {@link #getTemplateNamespacePrefix() template prefix} or not.
	 */
	@Override
	public List<String> getTemplatesStartingWith(String prefix, int limit) throws IOException {
		if (isNullString(prefix, "getTemplatesStartingWith") ||
				isInvalidNumber(limit, "getTemplatesStartingWith")) return Collections.emptyList();

		String templatePrefix = removePrefixIfExists(prefix, getTemplateNamespacePrefix());
		return getPagesStartingWith(templatePrefix, limit, WikiNamespace.TEMPLATE);
	}

	@Override
	public String getTemplateNamespacePrefix() {
		return currentLanguage.getTemplatePrefix();
	}

	/**
	 * {@inheritDoc}
	 * Note, that if parameter is <code>null</code> or empty, empty list will be returned.
	 * It is no matter if parameter will contain {@link #getTemplateNamespacePrefix() template prefix} or not.
	 */
	@Override
	public List<TemplateParameter> getTemplateParameters(String name) throws IOException {
		if (isInvalidString(name, "getTemplateParameters")) return Collections.emptyList();

		String templateName = addPrefixIfNotExists(name, getTemplateNamespacePrefix());
		URL request = new URL(apiUrl + templateDataUrl + formatJson + "&titles=" + normalize(templateName));
		return WikiQuery.getTemplatesParams(request);
	}

	private List<String> getPagesStartingWith(String prefix, int limit, WikiNamespace namespace) throws IOException {
		URL request = new URL(apiUrl + queryUrl + formatXml + "&list=allpages&apprefix=" + normalize(prefix) +
				"&apnamespace=" + namespace + "&aplimit=" + limit);
		return WikiQuery.getPages(request);
	}

	private String addPrefixIfNotExists(String arg, String prefix) {
		return arg.startsWith(prefix) ? arg.trim() : (prefix + arg).trim();
	}

	private String removePrefixIfExists(String arg, String prefix) {
		return arg.startsWith(prefix) ? arg.substring(prefix.length(), arg.length()).trim() : arg.trim();
	}

	private boolean isNullString(String arg, String methodName) {
		boolean result = arg == null;
		if (result) LOG.warning("Null string in " + methodName);
		return result;
	}

	private boolean isInvalidString(String arg, String methodName) {
		boolean result = isNullString(arg, methodName) || arg.isEmpty();
		if (result) LOG.warning("Invalid string in " + methodName);
		return result;
	}

	private boolean isInvalidNumber(long arg, String methodName) {
		boolean result = arg <= 0 || arg > BOT_QUERY_MAX_COUNT;
		if (result) LOG.warning("Invalid number in " + methodName);
		return result;
	}

	private String normalize(String arg) {
		try {
			return URLEncoder.encode(arg.trim(), StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// can not catch
		}
		return arg;
	}
}
