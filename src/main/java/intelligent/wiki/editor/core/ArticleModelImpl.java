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

import intelligent.wiki.editor.bot.compiler.AST.Content;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.util.Objects;

/**
 * Default implementation of {@link ArticleModel}.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public class ArticleModelImpl implements ArticleModel {
	private StringProperty text = new SimpleStringProperty();
	private StringProperty title = new SimpleStringProperty();
	private ObjectProperty<TreeItem<Content>> root = new SimpleObjectProperty<>();

	public ArticleModelImpl(WikiArticleParser parser) {
		Objects.requireNonNull(parser, "Null wiki article parser!");
		text.addListener((observable, oldValue, newValue) -> {
			WikiArticle wikiArticle = parser.parse(new ArticleImpl(articleTitle(), newValue));
			root.setValue(wikiArticle.getRoot());
		});
	}

	private String articleTitle() {
		String articleTitle = titleProperty().get();
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
	public StringProperty textProperty() {
		return text;
	}

	@Override
	public StringProperty titleProperty() {
		return title;
	}

	@Override
	public ObjectProperty<TreeItem<Content>> rootProperty() {
		return root;
	}
}
