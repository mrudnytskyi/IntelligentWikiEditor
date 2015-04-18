/*
 * FileFiltersManager.java	13.04.2015
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
package gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 13.04.2015
 */
public class FileFiltersManager {
	
	/**
	 * Text files filter for using in {@link JFileChooser file choosers}.
	 * 
	 * @author Mir4ik
	 * @version 0.1 16.03.2015
	 */
	private class TXTFileFilter extends FileFilter {

		@Override
		public boolean accept(File file) {
			boolean isFile = file.isFile();
			boolean isDir = file.isDirectory();
			String fileName = file.getName();
			boolean filterNameLower = fileName.endsWith("TXT");
			boolean filterNameUpper = fileName.endsWith("txt");
			if (isDir || (isFile) && (filterNameLower || filterNameUpper)) {
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return "TeXT files (TXT)";
		}
	}
	
	/**
	 * XML files filter for using in {@link JFileChooser file choosers}.
	 * 
	 * @author Mir4ik
	 * @version 0.1 16.03.2015
	 */
	private class XMLFileFilter extends FileFilter {

		@Override
		public boolean accept(File file) {
			boolean isFile = file.isFile();
			boolean isDir = file.isDirectory();
			String fileName = file.getName();
			boolean filterNameLower = fileName.endsWith("XML");
			boolean filterNameUpper = fileName.endsWith("xml");
			if (isDir || (isFile) && (filterNameLower || filterNameUpper)) {
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return "eXtensible Markup Language files (XML)";
		}
	}
	
	private FileFilter filters[] = {new TXTFileFilter(), new XMLFileFilter()};
	
	public FileFilter getFilter(String extension) {
		if (extension.equalsIgnoreCase("txt")) {
			return filters[0];
		}
		if (extension.equalsIgnoreCase("xml")) {
			return filters[1];
		}
		return null;
	}
}