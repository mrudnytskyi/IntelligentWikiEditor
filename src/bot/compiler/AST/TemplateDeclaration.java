package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents template declaration node.
 * 
 * @author Mir4ik
 * @version 0.1 22.11.2014
 */
/*
 * TODO
 * 1. use object of Template class instead String
 * 2. add template properties
 */
public class TemplateDeclaration implements Content {
	
	protected final CharSequence template;
	
	public TemplateDeclaration(CharSequence template) {
		Objects.requireNonNull(template, "Template can not be null!");
		this.template = template;
	}
	
	public CharSequence getTemplate() {
		return template;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{{");
		sb.append(template);
		sb.append("}}");
		return sb.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitTemplateDeclaration(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}