/*
 * RunnerClassifier.java	22.01.2015
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
package bot;

import java.io.File;
import java.io.FilenameFilter;

import bot.core.ApplicationException;
import bot.core.ArticleTemplate;
import bot.core.ArticlesCreator;
import bot.io.FilesFacade;
import bot.nlp.Snippet;

/**
 * Class for starting application. See <code>main(String[] args)</code> method 
 * documentation for arguments using.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.01.2015
 */
// TODO: will be removed later!
public class RunnerClassifier {

	private static class TXTFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			if (name.endsWith(".txt")) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Method for application start-up.
	 * 
	 * @param args	array with 3 parameters: directory with source files for 
	 * 				article, template file for article skeleton and article 
	 * 				file to save result. Note, that source files can be 
	 * 				divided into two parts: text fragment and source (for 
	 * 				example URL to it) using special divider characters: "---".
	 * @throws Exception	if some error occurs
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("Usage: <src directory> <template> <article>");
			System.exit(1);
		}
		String srcDir = args[0];
		String templateFile = args[1];
		String articleFile = args[2];
		File source = new File(srcDir);
		File template = new File(templateFile);
		File article = new File(articleFile);
		if (source.isFile() || template.isDirectory() || article.isDirectory()) {
			System.err.println("Wrong parameters!");
			System.exit(1);
		}
		File[] srcFiles = source.listFiles(new TXTFilter());
		Snippet[] snippets = new Snippet[srcFiles.length];
		int i = 0;
		for (File file : srcFiles) {
			String[] fileContent = FilesFacade.readTXT(file.getPath()).split("---");
			try {
				snippets[i] = new Snippet(fileContent[0], fileContent[1], null);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new ApplicationException(
						"Wrong file \"" + file + "\" format!", e);
			}
			i++;
		}
		ArticleTemplate at = null;
		try {
			at = (ArticleTemplate) FilesFacade.readXML(template.getPath());
		} catch (ClassCastException e) {
			throw new ApplicationException(
					"Wrong file \"" + template + "\" format!", e);
		}
		ArticlesCreator ac = new ArticlesCreator(snippets, at);
		FilesFacade.writeTXT(articleFile, ac.createArticle());
	}
}