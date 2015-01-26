package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Abstraction for every AST node.
 * 
 * @author Mir4ik
 * @version 0.1 20.11.2014
 */
public interface Content {

	void accept(Visitor visitor);

	CharSequence getWikiSource();
}