package intelligent.wiki.editor.bot.io;
/*
 * FilesFacade.java	17.01.2015
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

/**
 * Class, created for reading and writing files with different formats: plain
 * text and XML. Note, that all it's methods can throw {@link IOException}.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.2 17.01.2015
 */
public final class FilesFacade {
	
	/**
	 * do not instantiate this class
	 */
	private FilesFacade() {}
	
	/**
	 * Reads plain text from specified file.
	 * <p>
	 * Note, that {@link StandardCharsets#UTF_8 UTF-8} {@link Charset charset}
	 * is used to decode bytes.
	 * 
	 * @param file				path to file
	 * @return					file content in {@link String} representation
	 * @throws IOException		if an I/O error occurs
	 */
	public static String readTXT(String file) throws IOException {
		return new String(Files.readAllBytes(Paths.get(file)),
				StandardCharsets.UTF_8);
	}
	
	/**
	 * Writes plain text to specified file.
	 * <p>
	 * Note, that {@link StandardOpenOption#CREATE CREATE},
	 * {@link StandardOpenOption#TRUNCATE_EXISTING TRUNCATE_EXISTING} and 
	 * {@link StandardOpenOption#WRITE WRITE} 
	 * {@link StandardOpenOption open options} is used to open file.
	 * 
	 * @param file				path to file
	 * @param str				plain text in {@link String} representation
	 * @throws IOException		if an I/O error occurs
	 */
	public static void writeTXT(String file, String str) throws IOException {
		Files.write(Paths.get(file), str.getBytes());
	}
	
	/**
	 * Reads object from specified XML file.
	 * 
	 * @param file				path to file
	 * @return					deserialized from XML object 
	 * @throws IOException		if an I/O error occurs
	 */
	public static Object readXML(String file) throws IOException {
		try {
			XStream stream = new XStream();
			return stream.fromXML(new File(file));
		} catch (StreamException e) {
			throw new IOException(e);
		}
	}
	
	/**
	 * Writes specified object to specified XML file.
	 * 
	 * @param file				path to file
	 * @param obj				object to serialize in XML
	 * @throws IOException		if an I/O error occurs
	 */
	public static void writeXML(String file, Object obj) throws IOException {
		try (FileWriter writer = new FileWriter(file)) {
			XStream stream = new XStream();
			stream.toXML(obj, writer);
		}
	}
}