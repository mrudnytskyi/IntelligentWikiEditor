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

package intelligent.wiki.editor.gui.fx.dialogs;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Class, representing dialog to insert headingText.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.09.2015
 */
public class ModifyHeadingDialog extends InputDialog {

	private static final int HEADING_TYPES_COUNT = 5;
	private final String headingText;
	private final ToggleGroup radioButtons = new ToggleGroup();

	protected ModifyHeadingDialog(String headingText, String titleId, String headerId, String contentId, ResourceBundle i18n) {
		super(titleId, headerId, contentId, i18n);

		this.headingText = headingText == null ? "" : headingText.trim();
		getDialogPane().getStyleClass().add("choice-dialog");
		generateRadioButtons();
	}

	private void generateRadioButtons() {
		for (int i = 0; i < HEADING_TYPES_COUNT; i++) {
			char[] header = new char[i + 2];
			Arrays.fill(header, '=');
			String headerStr = String.valueOf(header);

			RadioButton rb = new RadioButton(String.join(" ", headerStr, headingText, headerStr));
			rb.setToggleGroup(radioButtons);
			content.add(rb, 0, i);

			if (i == 0) {
				rb.setSelected(true);
			}
		}
	}

	@Override
	public String getInputtedResult() {
		return ((RadioButton) radioButtons.getSelectedToggle()).getText().trim();
	}
}
