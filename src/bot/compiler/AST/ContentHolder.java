package bot.compiler.AST;

/**
 * Abstraction for every AST node with content.
 * 
 * @author Mir4ik
 * @version 0.1 20.11.2014
 */
public interface ContentHolder extends Content {
	
	Content[] getContent();
}