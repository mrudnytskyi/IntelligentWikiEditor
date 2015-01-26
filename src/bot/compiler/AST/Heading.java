package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class representing headings in article.
 * 
 * @author Mir4ik
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
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i != getType().ordinal() + 1) {
			sb.append("=");
			i++;
		}
		String temp = sb.toString();
		sb.append(super.toString());
		sb.append(temp);
		return sb.toString();
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