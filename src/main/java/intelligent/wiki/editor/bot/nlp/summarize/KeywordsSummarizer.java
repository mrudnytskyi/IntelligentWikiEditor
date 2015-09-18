package intelligent.wiki.editor.bot.nlp.summarize;
/*
 * KeywordsSummarizer.java	12.11.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
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

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * One of the simplest summarizer, which use {@link Set} of keywords to 
 * determine sentences for adding to summary.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 12.11.2014
 */
public class KeywordsSummarizer extends Summarizer {
	
	private Set<CharSequence> keywords;
	
	public KeywordsSummarizer(SummarizeDecider sd, Set<CharSequence> kw) {
		super(sd);
		setKeywords(kw);
	}
	
	public Set<CharSequence> getKeywords() {
		return keywords;
	}
	
	public void setKeywords(Set<CharSequence> keywords) {
		Objects.requireNonNull(keywords, "Keywords set can not be null!");
		this.keywords = keywords;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSuitable(CharSequence charSequence) {
		Iterator<CharSequence> i = keywords.iterator();
		while (i.hasNext()) {
			if (charSequence.toString().contains(i.next())) {
				return true;
			}
		}
		return false;
	}
}