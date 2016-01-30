/*
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
package intelligent.wiki.editor.sweble;

import intelligent.wiki.editor.bot.compiler.AST.CategoryDeclaration;
import intelligent.wiki.editor.bot.compiler.AST.Content;
import intelligent.wiki.editor.core.WikiArticle;
import intelligent.wiki.editor.sweble.AST.*;
import javafx.scene.control.TreeItem;
import org.sweble.wikitext.engine.nodes.EngPage;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.parser.nodes.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class, representing Wikipedia article realization using
 * <a href=https://github.com/sweble/sweble-wikitext>Sweble library</a> in application.
 * It is default implementation of {@link WikiArticle}.
 * See {@link SwebleWikiArticleParser} for this class objects creation details.
 *
 * @author Myroslav Rudnytskyi
 * @version 19.09.2015
 */
public class SwebleWikiArticle implements WikiArticle {

	private final EngPage content;

	public SwebleWikiArticle(EngProcessedPage content) {
		this.content = Objects.requireNonNull(content.getPage(), "Null wiki article content!");
	}

	@Override
	public List<CategoryDeclaration> getCategories() {
		//TODO make it
		return Collections.emptyList();
	}

	@Override
	public TreeItem<Content> getRoot() {
		return createTree(content);
	}

	private TreeItem<Content> createTree(WtNode node) {
		TreeItem<Content> tree;
		if (node instanceof WtBold) {
			tree = new TreeItem<>(new BoldSweble((WtBold) node));
		} else if (node instanceof WtItalics) {
			tree = new TreeItem<>(new ItalicsSweble((WtItalics) node));
		} else if (node instanceof WtExternalLink) {
			tree = new TreeItem<>(new ExternalLinkSweble((WtExternalLink) node));
		} else if (node instanceof WtInternalLink) {
			tree = new TreeItem<>(new InternalLinkSweble((WtInternalLink) node));
		} else if (node instanceof WtText) {
			tree = new TreeItem<>(new TextSweble((WtText) node));
		} else if (node instanceof WtSection) {
			tree = new TreeItem<>(new SectionSweble((WtSection) node));
//		} else if (node instanceof WtParagraph) {
//			tree = new TreeItem<>();
//		} else if (node instanceof WtOrderedList) {
//			tree = new TreeItem<>();
//		} else if (node instanceof WtUnorderedList) {
//			tree = new TreeItem<>();
		} else {
			tree = new TreeItem<>(new Node(node));
			node.stream().forEach(current -> tree.getChildren().add(createTree(current)));
		}
		return tree;
	}
}