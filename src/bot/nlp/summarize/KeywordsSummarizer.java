package bot.nlp.summarize;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * One of the simplest summarizer, which use {@link Set} of keywords to 
 * determine sentences for adding to summary.
 * 
 * @author Mir4ik
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