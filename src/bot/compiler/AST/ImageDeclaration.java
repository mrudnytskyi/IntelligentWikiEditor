/*
 * ImageDeclaration.java	22.11.2014
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
package bot.compiler.AST;

import java.util.Objects;

import utils.MutableString;
import bot.compiler.Visitor;

/**
 * Class represents image declaration node.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.11.2014
 */
// TODO: add image properties
public class ImageDeclaration implements Content {

	protected final CharSequence image;
	
	public ImageDeclaration(CharSequence image) {
		Objects.requireNonNull(image, "Image can not be null!");
		this.image = image;
	}
	
	public CharSequence getImage() {
		return image;
	}
	
	@Override
	public String toString() {
		MutableString ms = new MutableString("[[Файл:", image, "]]");
		return ms.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitImageDeclaration(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}