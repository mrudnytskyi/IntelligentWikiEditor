package bot.compiler.AST;

import java.util.Arrays;

import bot.compiler.Visitor;

/**
 * Class represents root of the AST tree for article.
 * 
 * @author Mir4ik
 * @version 0.1 17.1.2015
 */
public class Document extends AbstractContentHolder {

	public Document(Content[] content) {
		super(content);
	}
	
    @Override
    public String toString() {
    	return Arrays.deepToString(getContent());
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visitDocument(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}