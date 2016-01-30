package intelligent.wiki.editor.bot.io;
/*
 * DatabaseFacade.java	28.08.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
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

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class, created to get access to application database, named also knowledge
 * base storing all necessary data, to create articles. Note, that all it's 
 * methods can throw {@link IOException}.
 * <p>
 * Knowledge base contain such tables:
 * <li><b>replacement table</b> stores text snippets with links on Wikipedia
 * articles;
 * <li><b>books table</b></li> stores information about books to simplify 
 * adding literature to article.
 * 
 * @author Mir4ik
 * @version 0.2 28.08.2014
 */
@Deprecated
public final class DatabaseFacade {

	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static final String CONNECTION = 
			"jdbc:mysql://localhost:3306/wikibase";

	private static final String USER = "Wikibot";

	private static final String PASSWORD = "1234567890";

	private static final String CREATE_REPLACEMENT_TABLE = 
			"CREATE TABLE replacement("
			+ "replacement_id INT NOT NULL AUTO_INCREMENT, "
			+ "replacement_text VARCHAR(255) NOT NULL, "
			+ "replacement_link VARCHAR(255) NOT NULL,"
			+ "PRIMARY KEY(replacement_id))";

	private static final String ADD_REPLACEMENT = 
			"INSERT INTO replacement(replacement_text, replacement_link) "
			+ "VALUES (?, ?)";

	private static final String GET_REPLACEMENT = 
			"SELECT replacement_link FROM replacement "
			+ "WHERE replacement_text = ?";

	private static final String DELETE_REPLACEMENT_TABLE = 
			"DROP TABLE replacement";

	/**
	 * do not instantiate this class
	 */
	private DatabaseFacade() {}

	/**
	 * Creates connection to database using {@link #CONNECTION}, {@link #USER}
	 * and {@link #PASSWORD} constants and returns it. Note, that this method's
	 * clients need to close connection after work with it.
	 * 
	 * @return				connection to database object
	 * @throws IOException	if an I/O error occurs
	 */
	private static Connection getConnection() throws IOException {
		try {
			Class.forName(DRIVER);
			return (Connection) DriverManager.getConnection(CONNECTION, USER,
					PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Creates empty replacement table using {@link #CREATE_REPLACEMENT_TABLE}
	 * SQL expression.
	 * 
	 * @throws IOException	if an I/O error occurs
	 */
	public static void createReplacementTable() throws IOException {
		try (Connection conn = getConnection()) {
			try (Statement statement = (Statement) conn.createStatement()) {
				statement.execute(CREATE_REPLACEMENT_TABLE);
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Drops replacement table using {@link #DELETE_REPLACEMENT_TABLE} SQL 
	 * expression.
	 * 
	 * @throws IOException	if an I/O error occurs
	 */
	public static void deleteReplacementTable() throws IOException {
		try (Connection conn = getConnection()) {
			try (Statement statement = (Statement) conn.createStatement()) {
				statement.execute(DELETE_REPLACEMENT_TABLE);
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Checks, if replacement table exists.
	 * 
	 * @return				<code>true</code> if replacement table exists and
	 * 						<code>false</code> otherwise
	 * @throws IOException	if an I/O error occurs
	 */
	public static boolean existReplacementTable() throws IOException {
		try (Connection conn = getConnection()) {
			try (ResultSet result = conn.getMetaData().getTables(null, null,
					null, new String[] { "TABLE" })) {
				while (result.next()) {
					String name = result.getString(3);
					if (name.equals("replacement")) {
						return true;
					}
				}
				return false;
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Adds replacement pair to database. Note, that parameters can not be
	 * <code>null</code>.
	 * 
	 * @param t				text snippet
	 * @param l				link to Wikipedia article
	 * @throws IOException	if an I/O error occurs
	 */
	public static void addReplacement(String t, String l) throws IOException {
		Objects.requireNonNull(t, "Replacement text can not be null!");
		Objects.requireNonNull(l, "Replacement link can not be null!");
		try (Connection connection = getConnection()) {
			PreparedStatement statement = (PreparedStatement) 
					connection.prepareStatement(ADD_REPLACEMENT);
			statement.setString(1, t);
			statement.setString(2, l);
			statement.execute();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * Returns link to Wikipedia article, connected with specified text snippet 
	 * in database. Note, that parameters can not be <code>null</code>.
	 * 
	 * @param text			text snippet to search
	 * @return				link to Wikipedia article if specified text snippet
	 * 						is in database or <code>null</code> otherwise
	 * @throws IOException	if an I/O error occurs
	 */
	public static String[] getReplacement(String text) throws IOException {
		Objects.requireNonNull(text, "Replacement text can not be null!");
		List<String> results = new ArrayList<>();
		try (Connection connection = getConnection()) {
			PreparedStatement statement = (PreparedStatement) 
					connection.prepareStatement(GET_REPLACEMENT);
			statement.setString(1, text);
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					results.add(result.getString(1));
				}
				return results.toArray(new String[results.size()]);
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}
}