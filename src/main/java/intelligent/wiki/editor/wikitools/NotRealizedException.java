package intelligent.wiki.editor.wikitools;
/*
 * NotRealizedException.java	16.07.2015
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

import java.util.logging.Logger;

/**
 * Exception class, created to present situations when somebody try to run
 * unrealized functionality.
 * <p>
 * Note, that it is <i>unchecked</i> exception, so, don't write it in the
 * method's signature.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 16.07.2015
 */
public class NotRealizedException extends RuntimeException {

	private static final long serialVersionUID = -7311997396183080762L;

	private static final Logger log = Logger
			.getLogger(NotRealizedException.class.getName());

	public NotRealizedException() {
		doLog();
	}

	public NotRealizedException(String message) {
		super(message);
		doLog();
	}

	public NotRealizedException(Throwable cause) {
		super(cause);
		doLog();
	}

	public NotRealizedException(String message, Throwable cause) {
		super(message, cause);
		doLog();
	}

	private void doLog() {
		NotRealizedException.log.warning(getMessage());
	}
}