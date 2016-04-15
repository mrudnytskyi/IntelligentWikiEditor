/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
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
package intelligent.wiki.editor.io;

import intelligent.wiki.editor.io_impl.FilesFacade;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing {@link FilesFacade} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 30.01.2016
 * @see Test
 * @see Assert
 */
public class FilesFacadeTest {

	private final String strTestFile = "src/test/resources/strTestFile.txt";
	private final String objTestFile = "src/test/resources/objTestFile.xml";

	private String testStr = "Test string\r\nТрохи кирилиці";
	private XMLTestObject testObj = new XMLTestObject();

	@Test
	public void testTXT() throws Exception {
		FilesFacade.writeTXT(strTestFile, testStr);
		Assert.assertEquals(testStr, FilesFacade.readTXT(strTestFile));
	}

	@Test
	public void testXML() throws Exception {
		FilesFacade.writeXML(objTestFile, testObj);
		Assert.assertEquals(testObj, FilesFacade.readXML(objTestFile));
	}
}