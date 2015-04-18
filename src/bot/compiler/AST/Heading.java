/*
 * Heading.java	19.11.2014
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
 * Class representing headings in article.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.11.2014
 */
public class Heading extends AbstractContentHolder {
	
	public enum HeadingType {
		H1, H2, H3, H4, H5, H6;
	}
	
	protected final HeadingType type;

	public Heading(Content[] content, HeadingType type) {
		super(content);
		Objects.requireNonNull(type, "Type can not be null! Use one "
				+ "of the folowing: H1, H2, H3, H4, H5, H6");
		this.type = type;
	}
	
	public HeadingType getType() {
		return type;
	}
	
	public boolean isH1() {
		return getType().equals(HeadingType.H1);
	}
	
	public boolean isH2() {
		return getType().equals(HeadingType.H2);
	}
	
	public boolean isH3() {
		return getType().equals(HeadingType.H3);
	}
	
	public boolean isH4() {
		return getType().equals(HeadingType.H4);
	}
	
	public boolean isH5() {
		return getType().equals(HeadingType.H5);
	}
	
	public boolean isH6() {
		return getType().equals(HeadingType.H6);
	}
	
	@Override
	public String toString() {
		MutableString ms = new MutableString(super.toString());
		ms.append('=', getType().ordinal());
		return ms.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitHeading(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}