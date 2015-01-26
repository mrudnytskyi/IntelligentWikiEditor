package bot.nlp.processors;

import bot.nlp.TextFragment;

/**
 * Abstract class provides interface for every class, transforming 
 * {@link TextFragment} data. Interface provide 2 methods for getting results:
 * in array or as single {@code TextFragment} object.
 * 
 * @author Mir4ik
 * @version 0.1 20.1.2015
 */
/*
 * TODO:
 * 1. make with Decorator pattern?
 */
public abstract class TextProcessor {
	
	protected TextFragment[] result;
	
	public abstract void process(TextFragment tf);
	
	public TextFragment getResultFragment() {
		return result[0];
	}
	
	public TextFragment[] getResult() {
		return result;
	}
}