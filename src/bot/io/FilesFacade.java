package bot.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

	public static String read(String file) throws ApplicationException {
		return read(new File(file));
	}
	
	public static String read(File file) throws ApplicationException {
		StringBuilder sb = new StringBuilder();
		try {
			FileReader txtInput = new FileReader(file);
			int c = 0;
			try {
				while (c != -1) {
					c = txtInput.read();
					sb.append((char) c);
				}
			} finally {
				txtInput.close();
			}
		} catch (IOException e) {
			throw new ApplicationException(
					"Exception while reading " + file + " : ", e);
		}
		return sb.substring(0, sb.length() - 1).toString();
	}
	
	public static void write(String f, String str) throws ApplicationException {
		write(new File(f), str);
	}
	
	public static void write(File file, String s) throws ApplicationException {
		try {
			FileWriter writer = new FileWriter(file);
			try {
				writer.write(s);
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			throw new ApplicationException(
					"Exception while writing to " + file + " : ", e);
		}	
	}
}