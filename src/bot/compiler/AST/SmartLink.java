package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents smart link in article, such as [[Java]] or [[Java|Java]].
 * 
 * @author Mir4ik
 * @version 0.1 19.11.2014
 */
public class SmartLink implements Content {
	
	protected final CharSequence destination;
	
	protected final CharSequence caption;
	
	public SmartLink(CharSequence destination) {
		this(destination, null);
	}

	public SmartLink(CharSequence destination, CharSequence caption) {
		Objects.requireNonNull(destination, "Destination can not be null!");
		this.destination = destination;
		this.caption = caption;
	}
	
	public CharSequence getDestination() {
		return destination;
	}
	
	public CharSequence getCaption() {
		return caption;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[[");
		sb.append(destination);
		if (caption != null) {
			sb.append('|');
			sb.append(caption);
		}
		sb.append("]]");
		return sb.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitSmartLink(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}