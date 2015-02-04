package bot.core;

import java.util.Objects;

import bot.nlp.Snippet;
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
	
	private final Snippet[] src;
	
	private final Classifier classifier;
	
	public ArticlesCreator(Snippet[] src, ArticleTemplate template) {
		Objects.requireNonNull(src, "Text fragments array can not be null!");
		this.src = src;
		this.classifier = new Classifier(template);
	}
	
	public String createArticle() {
		Article at = new Article();
		for (Snippet s : src) {
			//refactore
			TextProcessor cleaner = new CleanProcessor();
			cleaner.process(s);
			Snippet ntf = cleaner.getResultSnippet();
			TextProcessor tokenizer = new SenceTokenizerProcessor();
			tokenizer.process(ntf);
			Snippet[] nntf = tokenizer.getResult();
			for (Snippet curr : nntf) {
				TextProcessor rem = new StopsRemoverProcessor();
				rem.process(curr);
				Snippet fin = rem.getResultSnippet();
				at.add(curr, classifier.classify(fin));
			}	
		}
		if (DEBUG) System.out.println(at);
		return at.toString();
	}
}