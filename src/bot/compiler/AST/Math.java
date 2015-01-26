package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Class represents AST node of mathematics expression.
 * 
 * @author Mir4ik
 * @version 0.1 13.11.2014
 */
public class Math extends Characters {

	public Math(CharSequence chars) {
		super(chars);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<math>");
		sb.append(super.toString());
		sb.append("</math>");
		return sb.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitMath(this);
	}
	
	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}