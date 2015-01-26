package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents category declaration node.
 * 
 * @author Mir4ik
 * @version 0.1 24.09.2014
 */
/*
 * TODO
 * 1. use object of Category class instead String
 */
public class CategoryDeclaration implements Content {
	
	protected final CharSequence category;
	
	public CategoryDeclaration(CharSequence category) {
		Objects.requireNonNull(category, "Category object can not be null!");
		this.category = category;
	}
	
	public CharSequence getCategory() {
		return category;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[[Категорія:");
		sb.append(category);
		sb.append("]]");
		return sb.toString();
	}

	@Override
	public String getWikiSource() {
		return toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitCategoryDeclaration(this);
	}
}