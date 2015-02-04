package bot.nlp.processors;

import bot.nlp.Snippet;

/**
 * Abstract class provides interface for every class, transforming 
 * {@link Snippet} data. Interface provide 2 methods for getting results:
 * in array or as single {@code Snippet} object.
 * 
 * @author Mir4ik
 * @version 0.1 20.1.2015
 */
/*
 * TODO:
 * 1. make with Decorator pattern?
 */
public abstract class TextProcessor {
	
	protected Snippet[] result;
	
	public abstract void process(Snippet snippet);
	
	public Snippet getResultSnippet() {
		return result[0];
	}
	
	public Snippet[] getResult() {
		return result;
	}
}