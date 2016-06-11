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

package intelligent.wiki.editor.server.io;

import intelligent.wiki.editor.server.io.api.HibernateOperations;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Myroslav Rudnytskyi
 * @version 11.06.2016
 * @see HibernateFacade
 */
public class HibernateFacadeTest {

	private HibernateOperations hibernate;

	@Before
	public void setUp() throws Exception {
		hibernate = new HibernateFacade();
	}

	@Test
	public void testShutdown() throws Exception {
		// setup
		SessionFactory sessionFactory = hibernate.getSessionFactory();
		// execute
		hibernate.shutdown();
		// verify
		assertTrue(sessionFactory.isClosed());
	}

	@Test
	public void testGetSessionFactory() throws Exception {
		// execute
		SessionFactory sessionFactory = hibernate.getSessionFactory();
		// verify
		assertThat(sessionFactory, is(notNullValue()));
	}
}