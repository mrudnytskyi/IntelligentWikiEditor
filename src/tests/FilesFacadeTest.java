package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import bot.io.FilesFacade;

/**
 * Class for testing {@link FilesFacade} class.
 * 
 * @author Mir4ik
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
	public void test() {
		String strTestFile = "strTestFile.txt";
		String objTestFile = "objTestFile.xml";
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