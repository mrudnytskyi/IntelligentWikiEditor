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

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 29.08.2014
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 3189658968868419110L;

	public ApplicationException() {}
	
	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(Throwable cause) {
		super(cause);
	}
	
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}