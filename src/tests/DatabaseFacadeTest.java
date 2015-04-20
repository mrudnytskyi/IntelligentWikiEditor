/*
 * DatabaseFacadeTest.java	18.04.2015
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

import junit.framework.Assert;

import org.junit.Test;

import bot.io.DatabaseFacade;

/**
 * Class for testing {@link DatabaseFacade} class.
 * 
 * @author Myroslav Rudnytskyi
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