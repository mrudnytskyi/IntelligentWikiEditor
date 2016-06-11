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
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Class provide functionality to access to the database using <a href="http://hibernate.org">Hibernate ORM</a>
 * and encapsulates common code to create {@code SessionFactory}.
 *
 * @author Myroslav Rudnytskyi
 * @version 11.06.2016
 */
public class HibernateFacade implements HibernateOperations {

	/**
	 * SessionFactory is set up once for an application!
	 */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		Configuration config = new Configuration().configure();
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		return config.buildSessionFactory(registry);
	}

	@Override
	public void shutdown() {
		getSessionFactory().close();
	}

	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
