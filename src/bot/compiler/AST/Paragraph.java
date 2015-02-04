package bot.compiler.AST;

import java.util.Arrays;

import bot.compiler.Visitor;

/**
 * Class represents some content, grouped together using special symbols.
 * 
 * @author Mir4ik
 * @version 0.1 4.2.2015
 */
public class Paragraph extends AbstractContentHolder {
	
    public Paragraph(Content[] content) {
        super(content);
    }
    
    @Override
    public String toString() {
    	return Arrays.deepToString(getContent());
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visitParagraph(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}