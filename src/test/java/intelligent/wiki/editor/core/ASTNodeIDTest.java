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

package intelligent.wiki.editor.core;

import intelligent.wiki.editor.core_api.ASTNodeID;
import intelligent.wiki.editor.spring.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Class for testing {@link ASTNodeID} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 12.02.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ASTNodeIDTest {
	@Inject
	private ASTNodeID id;
	@Inject
	private ASTNodeID anotherId;

	@Test
	public void testEquals() throws Exception {
		Assert.assertEquals(true, id.equals(id));
		Assert.assertEquals(false, id.equals(anotherId));
		Assert.assertEquals(false, id.equals(null));
		Assert.assertEquals(false, id.equals(new Object()));
	}
}