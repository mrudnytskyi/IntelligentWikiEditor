package bot.core;

import java.util.Objects;

import bot.nlp.TextFragment;
import bot.nlp.processors.CleanProcessor;
import bot.nlp.processors.SenceTokenizerProcessor;
import bot.nlp.processors.StopsRemoverProcessor;
import bot.nlp.processors.TextProcessor;

/**
 * Class was created to produce article.
 * 
 * @author Mir4ik
 * @version 0.1 18.1.2015
 */
/*
 * TODO extend class to create other article parts
 */
public class ArticlesCreator {
	
	private static final boolean DEBUG = true;
	
	private final TextFragment[] src;
	
	private final Classifier classifier;
	
	public ArticlesCreator(TextFragment[] src, ArticleTemplate template) {
		Objects.requireNonNull(src, "Text fragments array can not be null!");
		this.src = src;
		this.classifier = new Classifier(template);
	}
	
	public String createArticle() {
		Article at = new Article();
		for (TextFragment tf : src) {
			//refactore
			TextProcessor cleaner = new CleanProcessor();
			cleaner.process(tf);
			TextFragment ntf = cleaner.getResultFragment();
			TextProcessor tokenizer = new SenceTokenizerProcessor();
			tokenizer.process(ntf);
			TextFragment[] nntf = tokenizer.getResult();
			for (TextFragment curr : nntf) {
				TextProcessor rem = new StopsRemoverProcessor();
				rem.process(curr);
				TextFragment fin = rem.getResultFragment();
				at.add(curr, classifier.classify(fin));
			}	
		}
		if (DEBUG) System.out.println(at);
		return at.toString();
	}
}