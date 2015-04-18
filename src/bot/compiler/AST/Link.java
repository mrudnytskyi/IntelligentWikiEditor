/*
 * Link.java	19.11.2014
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import utils.MutableString;
import bot.compiler.Visitor;

/**
 * Class represents simple link in article, such as [http://google.com] or 
 * [http://google.com Google].
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.11.2014
 */
public class Link implements Content {
	
	protected final URL url;
	
	protected final CharSequence caption;
	
	public Link(URL url) {
		this(url, null);
	}
	
	public Link(CharSequence url) {
		this(url, null);
	}

	public Link(URL url, CharSequence caption) {
		Objects.requireNonNull(url, "URL can not be null!");
		this.url = url;
		this.caption = caption;
	}
	
	public Link(CharSequence url, CharSequence caption) {
		Objects.requireNonNull(url, "URL can not be null!");
		try {
			this.url = new URL(url.toString());
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
		this.caption = caption;
	}
	
	public CharSequence getCaption() {
		return caption;
	}
	
	public URL getURL() {
		return url;
	}
	
	@Override
	public String toString() {
		MutableString ms = new MutableString("[", url.toString());
		if (caption != null) {
			ms.append(" ", caption);
		}
		ms.append(']');
		return ms.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitLink(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}