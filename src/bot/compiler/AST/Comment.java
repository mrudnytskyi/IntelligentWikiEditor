package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Class represents simple comment.
 * 
 * @author Mir4ik
 * @version 0.1 20.11.2014
 */
public class Comment extends Characters {

	public Comment(CharSequence chars) {
		super(chars);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<!--");
		sb.append(getCharacters());
		sb.append("-->");
		return sb.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitComment(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}