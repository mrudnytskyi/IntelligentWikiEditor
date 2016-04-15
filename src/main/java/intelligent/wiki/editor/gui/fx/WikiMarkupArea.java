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

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.NavigationActions;

/**
 * Text area class, created to work with wiki markup as content.
 *
 * @author Myroslav Rudnytskyi
 * @version 29.01.2016
 */
public class WikiMarkupArea extends CodeArea implements ObservableCodeArea {

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
		setWrapCode(true);
		getStyleClass().add("wiki-area");
		// TODO add context menu functionality
	}

	@Override
	public void cut() {
		super.cut();
	}

	@Override
	public void copy() {
		super.copy();
	}

	@Override
	public void paste() {
		super.paste();
	}

	@Override
	public int getCodeLength() {
		return super.getLength();
	}

	@Override
	public String getCode() {
		return super.getText();
	}

	@Override
	public void setCode(String text) {
		replace(0, getCodeLength(), text);
	}

	@Override
	public int getCaretPos() {
		return super.getCaretPosition();
	}

	@Override
	public int getSelectionStart() {
		return super.getSelection().getStart();
	}

	@Override
	public int getSelectionEnd() {
		return super.getSelection().getEnd();
	}

	@Override
	public String getSelectedCode() {
		return super.getSelectedText();
	}

	@Override
	public String getTextBetween(int start, int end) {
		return super.getText(start, end);
	}

	@Override
	public boolean isWrapCode() {
		return super.isWrapText();
	}

	@Override
	public void setWrapCode(boolean value) {
		super.setWrapText(value);
	}

	@Override
	public void deselect() {
		super.deselect();
	}

	@Override
	public void selectAll() {
		super.selectAll();
	}

	@Override
	public void move(int pos) {
		super.moveTo(pos);
	}

	@Override
	public void moveLineStart() {
		super.lineStart(NavigationActions.SelectionPolicy.CLEAR);
	}

	@Override
	public void moveLineEnd() {
		super.lineEnd(NavigationActions.SelectionPolicy.CLEAR);
	}

	@Override
	public void moveStart() {
		super.start(NavigationActions.SelectionPolicy.CLEAR);
	}

	@Override
	public void moveEnd() {
		super.end(NavigationActions.SelectionPolicy.CLEAR);
	}

	@Override
	public void selectLine() {
		super.selectLine();
	}

	@Override
	public void append(String text) {
		super.appendText(text);
	}

	@Override
	public void insert(String text, int pos) {
		super.insertText(pos, text);
	}

	@Override
	public void delete(int start, int end) {
		super.deleteText(start, end);
	}

	@Override
	public void clear() {
		super.clear();
	}

	@Override
	public void replace(int start, int end, String text) {
		super.replaceText(start, end, text);
	}

	@Override
	public void replaceSelection(String text) {
		super.replaceSelection(text);
	}

	@Override
	public void moveSelectedText(int pos) {
		super.moveSelectedText(pos);
	}

	@Override
	public ObservableValue<String> codeProperty() {
		return super.textProperty();
	}

	@Override
	public ObservableValue<String> selectedCodeProperty() {
		return super.selectedTextProperty();
	}

	@Override
	public ObjectProperty<ContextMenu> contentMenuProperty() {
		return super.contextMenuProperty();
	}
}
