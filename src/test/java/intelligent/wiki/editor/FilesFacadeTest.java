package intelligent.wiki.editor;
/*
 * FilesFacadeTest.java	13.04.2015
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import intelligent.wiki.editor.bot.io.FilesFacade;

/**
 * Class for testing {@link FilesFacade} class.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 13.04.2015
 * @see Test
 * @see Assert
 */
public class FilesFacadeTest {
	
	private class Obj {
		
		private int number = 1994;
		
		private double[] array = {2.1, 0.3};
		
		private List<String> list = new ArrayList<String>();
		
		public Obj() {
			list.add("This");
			list.add("is");
			list.add("test");
			list.add("String");
			list.add("~!@#$%^&|:\"<>`\\';/.,");
		}
		
		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}
			if (!(object instanceof Obj)) {
				return false;
			}
			Obj casted = (Obj) object;
			if (casted.number == number && 
					Arrays.equals(casted.array, array) && 
					casted.list.equals(list)) {
						return true;
			}
			return false;
		}
	}

	@Test
	@Ignore
	public void test() {
		String strTestFile = "src/test/resources/strTestFile.txt";
		String objTestFile = "src/test/resources/objTestFile.xml";
		String testStr = "Test string\r\nТрохи кирилиці";
		Obj testObj = new Obj();
		String resStr = null;
		Obj resObj = null;
		try {
			FilesFacade.writeTXT(strTestFile, testStr);
			FilesFacade.writeXML(objTestFile, testObj);
			resStr = FilesFacade.readTXT(strTestFile);
			resObj = (Obj) FilesFacade.readXML(objTestFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(testStr, resStr);
		Assert.assertEquals(testObj, resObj);
	}
}