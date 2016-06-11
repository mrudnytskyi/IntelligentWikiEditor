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

import intelligent.wiki.editor.app_impl.WikiEditorVersion;
import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.Parser;
import intelligent.wiki.editor.core_api.Project;
import intelligent.wiki.editor.core_impl.WikiProject;
import intelligent.wiki.editor.core_impl.sweble.SwebleParser;
import intelligent.wiki.editor.gui.fx.TreeItemFactory;
import intelligent.wiki.editor.gui.fx.WikiEditorController;
import intelligent.wiki.editor.gui.fx.dialogs.DialogsFactory;
import intelligent.wiki.editor.io_api.wiki.WikiOperations;
import intelligent.wiki.editor.io_impl.wiki.WikiFacade;
import intelligent.wiki.editor.services_api.inspections.Problems;
import intelligent.wiki.editor.services_impl.inspections.ProblemsHolder;
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
		return new DialogsFactory(version(), wikiOperations());
	}

	@Bean
	public WikiEditorVersion version() {
		return new WikiEditorVersion();
	}

	@Bean
	@SuppressWarnings("unused") // used indirectly!
	public WikiEditorController wikiEditorController() {
		return new WikiEditorController(wikiOperations(), dialogsFactory(), project(), simpleTreeItemFactory());
	}

	@Bean
	public Problems problemsHolder() {
		return new ProblemsHolder();
	}

	@Bean
	public Project project() {
		return new WikiProject(parser(), problemsHolder());
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