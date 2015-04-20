/*
 * WikiRulesFactory.java	04.09.2014
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
package bot.business;

import bot.core.ApplicationException;
import bot.core.WikiArticle;

/**
 * Class, containing set of rules for checking article. To create new rule,
 * write subclass of {@link AbstractWikiRule} in these file.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 04.09.2014
 */
// TODO: write rules classes
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