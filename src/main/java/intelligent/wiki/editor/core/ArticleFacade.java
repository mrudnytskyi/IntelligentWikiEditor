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
package intelligent.wiki.editor.core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Default implementation of {@link ArticleOperations}.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public class ArticleFacade implements ArticleOperations {

	private static final Logger LOG = Logger.getGlobal();
	@Inject
	private final WikiArticleParser parser;
	private StringProperty articleText = new SimpleStringProperty();
	private StringProperty articleTitle = new SimpleStringProperty();
	private WikiArticle wikiArticle;

	public ArticleFacade(WikiArticleParser parser) {
		this.parser = parser;
		articleText.addListener((observable, oldValue, newValue) -> {
			wikiArticle = parser.parse(new ArticleImpl(articleTitle(), newValue));
			LOG.info(wikiArticle.toString());
		});
	}

	private String articleTitle() {
		String articleTitle = articleTitleProperty().get();
		boolean isInvalid = articleTitle == null || articleTitle.isEmpty();
		if (isInvalid) {
			return "unknown";
		} else {
			//TODO wpf constant DRY WikiEditorController
			String fileEnding = ".wpf";
			boolean isFromFile = articleTitle.endsWith(fileEnding);
			return isFromFile ? articleTitle.substring(0, articleTitle.length() - fileEnding.length()) : articleTitle;
		}
	}

	@Override
	public StringProperty articleTextProperty() {
		return articleText;
	}

	@Override
	public StringProperty articleTitleProperty() {
		return articleTitle;
	}
}
