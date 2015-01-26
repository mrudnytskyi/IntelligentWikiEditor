package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents every simple char sequence.
 * 
 * @author Mir4ik
 * @version 0.1 19.11.2014
 */
public class Characters implements Content {
	
	protected final CharSequence chars;

	public Characters(CharSequence chars) {
		Objects.requireNonNull(chars, "Char sequence can not be null!");
		this.chars = chars;
	}
	
	public CharSequence getCharacters() {
		return chars;
	}
	
	@Override
	public String toString() {
		return chars.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitCharacters(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}