package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Class represents AST node which is not processed by Wikipedia engine.
 * 
 * @author Mir4ik
 * @version 0.1 20.11.2014
 */
public class Nowiki extends Characters {

	public Nowiki(CharSequence chars) {
		super(chars);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<nowiki>");
		sb.append(getCharacters());
		sb.append("</nowiki>");
		return sb.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitNowiki(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}