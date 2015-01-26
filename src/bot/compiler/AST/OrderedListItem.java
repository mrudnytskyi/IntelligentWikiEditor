package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * Class represent one item of ordered list (1, 2, 3, ...).
 * 
 * @author Mir4ik
 * @version 0.1 21.11.2014
 */
public class OrderedListItem extends AbstractContentHolder {

	public OrderedListItem(Content[] content) {
		super(content);
	}
	
	@Override
	public String toString() {
		return "# " + super.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitOrderedListItem(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}