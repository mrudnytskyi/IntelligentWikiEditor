package bot.compiler;

import java.util.List;

/** 
 * Interface for class-divider any char stream to tokens. 
 * 
 * @author Mir4ik
 * @version 0.1 26.10.2014
 */
public interface Tokenizer {

	List<Token> tokenize(CharSequence sequence) throws CompilerException;
}