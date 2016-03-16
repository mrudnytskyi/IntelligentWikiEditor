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
import intelligent.wiki.editor.core_api.Article;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.util.Objects;

/**
 * Class for wrapping {@link Article} object to provide suitable interface for observing article changes in JavaFX
 * code. So, it is <a href=https://en.wikipedia.org/wiki/Adapter_pattern>adapter</a> for wiki article. Note, that
 * it is default implementation of {@link ObservableArticle}.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public class ObservableArticleAdapter implements ObservableArticle {

	private final StringProperty text = new SimpleStringProperty();
	private final StringProperty title = new SimpleStringProperty();
	private final ObjectProperty<TreeItem<ASTNode>> root = new SimpleObjectProperty<>();
	private final Article article;
	private final TreeItemFactory<ASTNode> treeItemFactory;

	public ObservableArticleAdapter(Article article, TreeItemFactory<ASTNode> treeItemFactory) {
		this.article = Objects.requireNonNull(article, "Null article!");
		this.treeItemFactory = Objects.requireNonNull(treeItemFactory, "Null tree item factory!");
		enableASTUpdating();
		//TODO listener for root property, updating text on AST change
	}

	private void enableASTUpdating() {
		text.addListener((observable, oldValue, newValue) -> {
			root.setValue(treeItemFactory.wrapNode(article.getASTRoot()));
		});
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
