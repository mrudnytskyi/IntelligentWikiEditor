package bot.compiler.AST;

import bot.compiler.Visitor;

/**
 * 
 * 
 * @author Mir4ik
 * @version 0.1 4.2.2015
 */
/*
 * TODO
 * 1. creating Nowiki object in constructor?
 */
public class Pre implements Content {

	private Nowiki nowiki;

	public Pre(Nowiki nowiki) {
		this.nowiki = nowiki;
	}

	public Nowiki getNowiki() {
		return nowiki;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitPre(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}
