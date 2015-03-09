package bot.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

import bot.core.ApplicationException;

/**
 * Utility class for reading files.
 * 
 * @author Mir4ik
 * @version 0.1 17.1.2015
 */
/*
 * TODO:
 * 1. readTXT() works only with UTF-8 files. Add encoding?
 */
public final class FilesFacade {
	
	private FilesFacade() {}

	public static String readTXT(String file) throws ApplicationException {
		return readTXT(new File(file));
	}
	
	public static String readTXT(File file) throws ApplicationException {
		StringBuilder sb = new StringBuilder();
		try (FileReader txtInput = new FileReader(file)) {
			int c = 0;
			while (c != -1) {
				c = txtInput.read();
				sb.append((char) c);
			}
		} catch (IOException e) {
			throw new ApplicationException(
					"Exception while reading " + file + " : ", e);
		}
		return sb.substring(0, sb.length() - 1).toString();
	}
	
	public static void writeTXT(String f, String str) throws ApplicationException {
		writeTXT(new File(f), str);
	}
	
	public static void writeTXT(File file, String s) throws ApplicationException {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(s);
		} catch (IOException e) {
			throw new ApplicationException(
					"Exception while writing to " + file + " : ", e);
		}	
	}
	
	public static Object readXML(String file) throws ApplicationException {
		return readXML(new File(file));
	}
	
	public static Object readXML(File file) throws ApplicationException {
		try {
			XStream stream = new XStream();
			return stream.fromXML(file);
		} catch (XStreamException e) {
			throw new ApplicationException(
					"Exception while reading " + file + " : ", e);
		}
	}
	
	public static void writeXML(String f, Object o) throws ApplicationException {
		writeXML(new File(f), o);
	}
	
	public static void writeXML(File f, Object o) throws ApplicationException {
		try (FileWriter writer = new FileWriter(f)) {
			XStream stream = new XStream();
			stream.toXML(o, writer);
		} catch (Exception e) {
			throw new ApplicationException(
					"Exception while writing to " + f + " : ", e);
		}
	}
}