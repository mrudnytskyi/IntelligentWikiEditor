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
package intelligent.wiki.editor.gui.fx;

import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.Parser;
import intelligent.wiki.editor.core_api.Title;
import intelligent.wiki.editor.core_impl.WikiMarkup;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.util.Objects;

/**
 * Default implementation of {@link ObservableArticle}.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public class ObservableArticleImpl implements ObservableArticle {
	private final StringProperty text = new SimpleStringProperty();
	private final StringProperty title = new SimpleStringProperty();
	private final ObjectProperty<TreeItem<ASTNode>> root = new SimpleObjectProperty<>();

	public ObservableArticleImpl(Parser parser, TreeItemFactory<ASTNode> treeItemFactory) {
		Objects.requireNonNull(parser, "Null parser!");
		Objects.requireNonNull(treeItemFactory, "Null tree item factory!");
		text.addListener((observable, oldValue, newValue) -> {
			ASTNode wikiArticle = parser.parse(new WikiMarkup(newValue));
			root.setValue(treeItemFactory.wrapNode(wikiArticle));
		});
		//TODO listener for root property, updating text on AST change
	}

	private String articleTitle() {
		String articleTitle = titleProperty().get();
		boolean isInvalid = articleTitle == null || articleTitle.isEmpty();
		if (isInvalid) {
			return Title.NO_TITLE;
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
	public ObjectProperty<TreeItem<ASTNode>> rootProperty() {
		return root;
	}
}
