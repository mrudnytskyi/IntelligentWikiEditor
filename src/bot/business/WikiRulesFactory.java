package bot.business;

import bot.core.ApplicationException;
import bot.core.WikiArticle;

/**
 * Class, containing set of rules for checking article. To create new rule,
 * write subclass of {@link AbstractWikiRule} in these file.
 * 
 * @author Mir4ik
 * @version 0.1 04.09.2014
 */
/*
 * TODO
 * 1. write rules classes
 */
public class WikiRulesFactory {
	
	private final AbstractWikiRule[] rules; 
	
	protected class CorrectOrderSectionRule extends AbstractWikiRule {

		@Override
		public CheckResult check(WikiArticle article) {
			return new CheckResult("Not realized yet!");
		}
	}
	
	public WikiRulesFactory() throws ApplicationException {
		Class<? extends WikiRulesFactory> clazz = getClass();
		Class<?>[] declaredClasses = clazz.getDeclaredClasses();
		rules = new AbstractWikiRule[declaredClasses.length];
		int i = 0;
		for (Class<?> temp : declaredClasses) {
			try {
				rules[i] = (AbstractWikiRule) temp.newInstance();
			} catch (Exception e) {
				throw new ApplicationException("Check factory class!", e);
			}
			i++;
		}
	}
	
	public AbstractWikiRule[] getWikiRules() {
		return rules;
	}
}