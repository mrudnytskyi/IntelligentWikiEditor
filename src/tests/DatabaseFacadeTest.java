package tests;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import bot.io.DatabaseFacade;

/**
 * Class for testing {@link DatabaseFacade} class.
 * 
 * @author Mir4ik
 * @version 0.1 18.04.2015
 * @see Test
 * @see Assert
 */
public class DatabaseFacadeTest {
	
	@Test
	public void test() {
		String[] result = new String[] {};
		try {
			//DatabaseFacade.deleteReplacementTable();
			if (!DatabaseFacade.existReplacementTable()) {
				DatabaseFacade.createReplacementTable();
				DatabaseFacade.addReplacement("test", "test_link");
			}
			result = DatabaseFacade.getReplacement("test");
		} catch (IOException e) {
			System.out.println(e);
		}
		Assert.assertEquals(1, result.length);
		Assert.assertEquals("test_link", result[0]);
	}
}