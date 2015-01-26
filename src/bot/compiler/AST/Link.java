package bot.compiler.AST;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents simple link in article, such as [http://google.com] or 
 * [http://google.com Google].
 * 
 * @author Mir4ik
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
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		sb.append(url);
		if (caption != null) {
			sb.append(' ');
			sb.append(caption);
		}
		sb.append(']');
		return sb.toString();
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