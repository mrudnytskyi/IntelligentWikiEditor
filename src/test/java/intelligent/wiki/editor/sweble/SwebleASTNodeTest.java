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

package intelligent.wiki.editor.sweble;

import intelligent.wiki.editor.spring.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Class for testing {@link SwebleASTNode} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 12.02.2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class SwebleASTNodeTest {
	@Inject
	private SwebleASTNode astNode;
	@Inject
	private SwebleASTNode childAstNode;

	@Test(expected = NullPointerException.class)
	public void textConstructor() throws Exception {
		new SwebleASTNode(null, astNode);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddChildException() throws Exception {
		astNode.addChild(astNode);
	}

	@Test
	public void testAddChild() throws Exception {
		astNode.addChild(childAstNode);
		Assert.assertEquals(true, astNode.iterator().next().equals(childAstNode));
	}

	@Test
	public void testEquals() throws Exception {
		Assert.assertEquals(true, astNode.equals(astNode));
		Assert.assertEquals(false, astNode.equals(childAstNode));
		Assert.assertEquals(false, astNode.equals(null));
		Assert.assertEquals(false, astNode.equals(new Object()));
	}
}