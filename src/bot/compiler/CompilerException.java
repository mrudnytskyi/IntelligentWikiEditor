/*
 * CompilerException.java	17.01.2015
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
package bot.compiler;

/**
 * Class, representing exception while article parsing.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 17.01.2015
 */
// TODO: delete because of using Exception in application logic!
public class CompilerException extends Exception {

	private static final long serialVersionUID = 1273744087199630175L;

	private final int context;
	
	public int getContext() {
		return context;
	}
	
	public CompilerException() {
		super();
		context = 0;
	}
	
	public CompilerException(String message) {
		super(message);
		context = 0;
	}
	
	public CompilerException(String message, Throwable cause) {
		super(message, cause);
		context = 0;
	}
	
	public CompilerException(String message, int context) {
		super(message);
		this.context = context;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Symbol #" + context;
	}
}