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
package intelligent.wiki.editor.core_api;

/**
 * Interface for parser, transforming markup text objects ({@link MarkupText}) into abstract syntax tree (AST)
 * nodes ({@link ASTNode}).
 *
 * @author Myroslav Rudnytskyi
 * @version 24.10.2015
 * @see intelligent.wiki.editor.core_impl.sweble.SwebleParser
 */
public interface Parser {

	/**
	 * Method for creating wiki article object.
	 *
	 * @param article object, containing wiki text
	 * @return AST root object
	 * @throws ParserException if an exception occurs
	 */
	ASTNode parse(MarkupText article) throws ParserException;

	/**
	 * Class for wrapping any exceptions, thrown during parsing. Note, that it is <b>unchecked</b> exception.
	 */
	class ParserException extends RuntimeException {

		public ParserException() {
		}

		public ParserException(String message) {
			super(message);
		}

		public ParserException(String message, Throwable cause) {
			super(message, cause);
		}

		public ParserException(Throwable cause) {
			super(cause);
		}

		public ParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}
	}
}
