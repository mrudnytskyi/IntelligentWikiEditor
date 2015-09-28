package intelligent.wiki.editor.gui;
/*
 * Project.java	21.04.2015
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

import intelligent.wiki.editor.bot.nlp.Snippet;

/**
 * Class encapsulates paths to necessary for project files.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 21.04.2015
 */
@Deprecated
public class Project {

	private final String locationFolder;

	private final String templateFile;

	private final String articleFile;

	private final String srcFolder;

	/**
	 * Construct the project object with specified parameters.
	 * 
	 * @param locationFolder
	 *            where is project root folder situated
	 * @param templateFile
	 *            where is template file situated
	 * @param articleFile
	 *            where would application save created article
	 * @param srcFolder
	 *            where is {@link Snippet snippets} folder situated. Usually
	 *            <code>.../project_root/src</code>
	 */
	public Project(String locationFolder, String templateFile,
			String articleFile, String srcFolder) {

		// TODO: checks?
		this.locationFolder = locationFolder;
		this.templateFile = templateFile;
		this.articleFile = articleFile;
		this.srcFolder = srcFolder;
	}

	public String getLocationFolder() {
		return locationFolder;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public String getArticleFile() {
		return articleFile;
	}

	public String getSrcFolder() {
		return srcFolder;
	}
}