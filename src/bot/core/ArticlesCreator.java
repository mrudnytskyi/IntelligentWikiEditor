/*
 * ArticlesCreator.java	18.01.2015
 * Copyright (C) 2015 Myroslav Rudnytskyi
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
package bot.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import bot.nlp.Snippet;
import bot.nlp.SnippetTopic;
import bot.nlp.processors.CleanProcessor;
import bot.nlp.processors.SenceTokenizerProcessor;
import bot.nlp.processors.StopsRemoverProcessor;
import bot.nlp.processors.TextProcessor;

/**
 * Class was created to produce article.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 18.01.2015
 */
public class ArticlesCreator {

	private static final boolean DEBUG = true;

	private final Snippet[] src;

	private final Classifier classifier;

	private final Map<SnippetTopic, List<Snippet>> article =
			new HashMap<SnippetTopic, List<Snippet>>();

	public ArticlesCreator(Snippet[] src, ArticleTemplate template) {
		Objects.requireNonNull(src, "Text fragments array can not be null!");
		this.src = src;
		classifier = new Classifier(template);
	}

	private void add(Snippet snippet, SnippetTopic topic) {
		if (article.get(topic) == null) {
			article.put(topic, new ArrayList<Snippet>());
		}
		article.get(topic).add(snippet);
	}

	public String createArticle() {
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
				add(curr, classifier.classify(fin));
			}
		}
		if (ArticlesCreator.DEBUG) {
			System.out.println(this);
		}
		return toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<SnippetTopic, List<Snippet>>> i =
				article.entrySet().iterator();
		while (i.hasNext()) {
			Entry<SnippetTopic, List<Snippet>> current = i.next();
			sb.append("\r\n== ");
			sb.append(current.getKey());
			sb.append(" ==\r\n");
			Iterator<Snippet> iter = current.getValue().iterator();
			while (iter.hasNext()) {
				sb.append(iter.next().getText());
			}
		}
		return sb.toString();
	}
}