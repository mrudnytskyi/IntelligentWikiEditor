package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Class represents simple literal.
 * 
 * @author Mir4ik
 * @version 0.1 19.11.2014
 */
public class Literal extends AbstractContentHolder {

	public Literal(Content[] content) {
		super(content);
	}
	
	@Override
	public String toString() {
		return " " + super.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitLiteral(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}