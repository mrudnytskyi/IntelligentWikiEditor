package bot.nlp.summarize;

/**
 * Implementing this interface allows every Summarizer decide if concrete
 * {@link CharSequence} is suitable for text summary.
 * 
 * @author Mir4ik
 * @version 0.1 12.11.2014
 */
public interface SummarizeDecider {

	/**
	 * Method for deciding if concrete {@link CharSequence} is suitable for
	 * adding it into results pool. 
	 * 
	 * @param charSequence	char sequence for deciding
	 * @return true if parameter is suitable for summary, false - otherwise
	 */
	boolean isSuitable(CharSequence charSequence);
}
