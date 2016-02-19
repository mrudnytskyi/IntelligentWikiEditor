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

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

/**
 * Text area class, created to work with wiki markup as content.
 *
 * @author Myroslav Rudnytskyi
 * @version 29.01.2016
 */
public class WikiMarkupArea extends CodeArea {

	public WikiMarkupArea() {
		super();
		init();
	}

	/**
	 * Constructor to create text area using specified content.
	 *
	 * @param text content
	 */
	public WikiMarkupArea(String text) {
		super(text);
		init();
	}

	private void init() {
		setParagraphGraphicFactory(LineNumberFactory.get(this));
		setWrapText(true);
		getStyleClass().add("wiki-area");
		// TODO add context menu functionality
	}

	/**
	 * Method for replacing content of the text area using specified.
	 *
	 * @param text new content
	 */
	public void setText(String text) {
		clear();
		appendText(text);
	}

	/**
	 * Method moves caret to text area start position.
	 */
	public void moveCaretToStart() {
		positionCaret(0);
	}
}
