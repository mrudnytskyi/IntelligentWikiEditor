/*
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
package intelligent.wiki.editor.core;

/**
 * Interface for parser, creating {@link WikiArticle} object.
 *
 * @author Myroslav Rudnytskyi
 * @version 24.10.2015
 */
public interface WikiArticleParser {

	/**
	 * Method for creating wiki article object.
	 *
	 * @param article object, containing wiki text
	 * @return AST tree, packed in wiki article object
	 * @throws ParserException if an exception occurs
	 */
	WikiArticle parse(Article article) throws ParserException;

	class ParserException extends RuntimeException {

		public ParserException(String message) {
			super(message);
		}

		public ParserException(String message, Throwable cause) {
			super(message, cause);
		}

		public ParserException(Throwable cause) {
			super(cause);
		}
	}
}
