package bot.nlp.summarize;

import java.util.LinkedList;
import java.util.List;

/**
 * Class, representing abstraction for every automatic text summarizer. It was
 * implemented using <a href="http://en.wikipedia.org/wiki/Decorator pattern">
 * Decorator pattern</a>. So, you may create subclasses and put them one into 
 * another to combine different summarization algorithms.
 * 
 * @author Mir4ik
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