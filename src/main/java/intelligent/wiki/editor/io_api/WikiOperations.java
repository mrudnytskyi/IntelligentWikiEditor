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
package intelligent.wiki.editor.io_api;

import java.io.IOException;
import java.util.List;

/**
 * Interface specifying set of Wikipedia operations. Note, that it's methods can throw
 * {@link IOException} and their results depend on current configured language.
 *
 * @author Myroslav Rudnytskyi
 * @version 01.10.2015
 */
public interface WikiOperations {

	/**
	 * Returns <code>true</code> if there is article with specified name and
	 * <code>false</code> otherwise. Note, that article name depends on chosen
	 * language.
	 *
	 * @param name specified article name
	 * @return is article with specified name or not
	 * @throws IOException if an I/O error occurs
	 */
	boolean existsArticle(String name) throws IOException;

	/**
	 * Returns list of article names, starting with specified prefix. Note,
	 * that article name depends on chosen language.
	 *
	 * @param prefix specified prefix
	 * @param limit returned list size
	 * @return list of pages, starting with specified prefix
	 * @throws IOException if an I/O error occurs
	 */
	List<String> getArticlesStartingWith(String prefix, int limit) throws IOException;

	/**
	 * Returns plain wiki text of specified article. Note, that article name
	 * depends on chosen language.
	 *
	 * @param name full article name in chosen language
	 * @return wiki text in {@link String} representation
	 * @throws IOException if an I/O error occurs
	 */
	String getArticleContent(String name) throws IOException;

	/**
	 * Returns <code>true</code> if there is category with specified name and
	 * <code>false</code> otherwise. Note, that category name depends on chosen
	 * language.
	 *
	 * @param name specified category name
	 * @return is category with specified name or not
	 * @throws IOException if an I/O error occurs
	 */
	boolean existsCategory(String name) throws IOException;

	/**
	 * Returns list of categories names, starting with specified prefix. Note,
	 * that category name depends on chosen language and will include
	 * {@link #getCategoryNamespacePrefix() special string marker} before actual name.
	 *
	 * @param prefix specified prefix
	 * @param limit returned list size
	 * @return list of pages, starting with specified prefix
	 * @throws IOException if an I/O error occurs
	 */
	List<String> getCategoriesStartingWith(String prefix, int limit) throws IOException;

	/**
	 * Returns special string marker, used to show, that this name is used in
	 * wiki categories namespace. Note, that it depends on chosen language.
	 *
	 * @return string marker and colon, e. g. "Category:" for english wiki
	 */
	String getCategoryNamespacePrefix();

	/**
	 * Returns <code>true</code> if there is template with specified name and
	 * <code>false</code> otherwise. Note, that template name depends on chosen
	 * language.
	 *
	 * @param name specified template name
	 * @return is template with specified name or not
	 * @throws IOException if an I/O error occurs
	 */
	boolean existsTemplate(String name) throws IOException;

	/**
	 * Returns list of templates names, starting with specified preffix. Note,
	 * that template name depends on chosen language and will include
	 * {@link #getTemplateNamespacePrefix() special string marker} before actual name.
	 *
	 * @param prefix specified prefix
	 * @param limit returned list size
	 * @return list of pages, starting with specified prefix
	 * @throws IOException if an I/O error occurs
	 */
	List<String> getTemplatesStartingWith(String prefix, int limit) throws IOException;

	/**
	 * Returns special string marker, used to show, that this name is used in
	 * wiki templates namespace. Note, that it depends on chosen language.
	 *
	 * @return string marker and colon, e. g. "Template:" for english wiki
	 */
	String getTemplateNamespacePrefix();

	/**
	 * Returns template parameters list for specified template. Note, that
	 * template name depends on chosen language.
	 *
	 * @param name specified template name
	 * @return list of template parameter objects
	 * @throws IOException if an I/O error occurs
	 */
	List<TemplateParameter> getTemplateParameters(String name) throws IOException;
}
