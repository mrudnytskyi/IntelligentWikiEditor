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
package intelligent.wiki.editor.spring;

import intelligent.wiki.editor.bot.io.wiki.WikiFacade;
import intelligent.wiki.editor.bot.io.wiki.WikiOperations;
import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.Parser;
import intelligent.wiki.editor.core_impl.sweble.SwebleParser;
import intelligent.wiki.editor.gui.fx.ObservableArticle;
import intelligent.wiki.editor.gui.fx.ObservableArticleImpl;
import intelligent.wiki.editor.gui.fx.TreeItemFactory;
import intelligent.wiki.editor.gui.fx.WikiEditorController;
import intelligent.wiki.editor.gui.fx.dialogs.DialogsFactory;
import javafx.scene.control.TreeItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

/**
 * Spring configuration class for application.
 *
 * @author Myroslav Rudnytskyi
 * @version 23.10.2015
 */
@Configuration
public class Config {

	@Bean
	public WikiOperations wikiOperations() {
		return new WikiFacade(new Locale("uk"));
	}

	@Bean
	public DialogsFactory dialogsFactory() {
		return new DialogsFactory();
	}

	@Bean
	@SuppressWarnings("unused") // used indirectly!
	public WikiEditorController wikiEditorController() {
		return new WikiEditorController(wikiOperations(), dialogsFactory(), articleModel());
	}

	@Bean
	public ObservableArticle articleModel() {
		return new ObservableArticleImpl(parser(), simpleTreeItemFactory());
	}

	@Bean
	public TreeItemFactory<ASTNode> simpleTreeItemFactory() {
		return TreeItem::new;
	}

	@Bean
	public Parser parser() {
		return new SwebleParser();
	}

}