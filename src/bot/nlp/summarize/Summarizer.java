/*
 * Summarize.java	12.11.2014
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
package bot.nlp.summarize;

import java.util.LinkedList;
import java.util.List;

/**
 * Class, representing abstraction for every automatic text summarizer. It was
 * implemented using <a href="http://en.wikipedia.org/wiki/Decorator pattern">
 * Decorator pattern</a>. So, you may create subclasses and put them one into 
 * another to combine different summarization algorithms.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 12.11.2014
 */
public class Summarizer implements SummarizeDecider {
	
	private SummarizeDecider decorator;
	
	public Summarizer(SummarizeDecider decorator) {
		setDecorator(decorator);
	}
	
	public SummarizeDecider getDecorator() {
		return decorator;
	}
	
	public void setDecorator(SummarizeDecider decorator) {
		this.decorator = decorator;
	}
	
	/**
	 * Method for starting 
	 *  
	 * @param sources array, containing all proposed {@link CharSequence}s
	 * @return array, containing {@link CharSequence}s, chosen for summary
	 */
	public CharSequence[] summarize(CharSequence[] sources) {
		List<CharSequence> list = new LinkedList<CharSequence>();
		for (int i = 0; i < sources.length; i++) {
			if (decorator.isSuitable(sources[i])) {
				list.add(sources[i]);
			}
		}
		CharSequence[] result = new CharSequence[list.size()];
		return list.toArray(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSuitable(CharSequence charSequence) {
		return decorator.isSuitable(charSequence);
	}
}