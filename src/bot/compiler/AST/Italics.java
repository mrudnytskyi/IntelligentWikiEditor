package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Class represents content, written with <i>emphasis font</i>.
 * 
 * @author Mir4ik
 * @version 0.1 19.11.2014
 */
public class Italics extends AbstractContentHolder {

	public Italics(Content[] content) {
		super(content);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("''");
		sb.append(super.toString());
		sb.append("''");
		return sb.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitItalics(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}