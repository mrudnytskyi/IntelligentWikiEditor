/*
 * MediaWikiFacadeTest.java	14.04.2015
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
package tests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import bot.io.FilesFacade;
import bot.io.MediaWikiFacade;
import bot.io.MediaWikiFacade.Language;

/**
 * Class for testing {@link MediaWikiFacade} class.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 14.04.2015
 * @see Test
 * @see Assert
 */
public class MediaWikiFacadeTest {
	
	@Test
	public void test() {
		MediaWikiFacade.setLanguage(Language.UKRAINIAN);
		String text = null;
		String file = "articleTestFile.txt";
		try {
			text = MediaWikiFacade.getArticleText(FilesFacade.readTXT(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(text);
		System.out.println(text);
	}
}