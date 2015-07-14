/*
 * ApplicationException.java	29.08.2014
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
package bot.core;

import java.util.logging.Logger;

/**
 * Exception class, created to present wrong situations on application level.
 * Typical usage of it is to wrap low-level exceptions.
 * <p>
 * Note, that it is <i>unchecked</i> exception, so, don't write it in the
 * method's signature.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 29.08.2014
 * @see RuntimeException
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 3189658968868419110L;

	private static final Logger log = Logger
			.getLogger(ApplicationException.class.getName());

	public ApplicationException() {
		doLog();
	}

	public ApplicationException(String message) {
		super(message);
		doLog();
	}

	public ApplicationException(Throwable cause) {
		super(cause);
		doLog();
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
		doLog();
	}

	private void doLog() {
		ApplicationException.log.warning(getMessage());
	}
}