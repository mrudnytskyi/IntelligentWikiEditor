package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Class represents AST node with indent.
 * 
 * @author Mir4ik
 * @version 0.1 19.11.2014
 */
public class Indent extends AbstractContentHolder {

	public Indent(Content[] content) {
		super(content);
	}
	
	@Override
	public String toString() {
		return ": " + super.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitIndent(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}