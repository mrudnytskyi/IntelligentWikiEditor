package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents AST node of code snippet.
 * 
 * @author Mir4ik
 * @version 0.1 19.11.2014
 */
/*
 * TODO
 * 1. make language recognizing mechanism
 */
public class Code extends Characters {
	
	public enum Language {
		JAVA5, CXX, C_SHARP, C, UNKNOWN;
	}
	
	protected final Language language;

	public Code(CharSequence chars, Language language) {
		super(chars);
		Objects.requireNonNull(language, "Language can not be null!");
		this.language = language;
	}

	public Language getLanguage() {
		return language;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (language.equals(Language.UNKNOWN)) {
			sb.append("<source");
		} else {
			sb.append("<source lang = ");
			sb.append(language.toString().toLowerCase());
		}
		sb.append('>');
		sb.append(super.toString());
		sb.append("</source>");
		return sb.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitCode(this);
	}
	
	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}