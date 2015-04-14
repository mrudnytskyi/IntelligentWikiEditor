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
 * @author Mir4ik
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