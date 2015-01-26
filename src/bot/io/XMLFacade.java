package bot.io;

import java.io.File;
import java.io.FileWriter;

import bot.core.ApplicationException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

/**
 * 
 * @author Mir4ik
 * @version 0.1 25.1.2015
 */
/*
 * TODO add to FileFacade?
 */
public final class XMLFacade {
	
	private XMLFacade() {}

	public static Object read(String file) throws ApplicationException {
		return read(new File(file));
	}
	
	public static Object read(File file) throws ApplicationException {
		try {
			XStream stream = new XStream();
			return stream.fromXML(file);
		} catch (XStreamException e) {
			throw new ApplicationException(
					"Exception while reading " + file + " : ", e);
		}
	}
	
	public static void write(String f, Object o) throws ApplicationException {
		write(new File(f), o);
	}
	
	public static void write(File f, Object o) throws ApplicationException {
		try {
			XStream stream = new XStream();
			stream.toXML(o, new FileWriter(f));
		} catch (Exception e) {
			throw new ApplicationException(
					"Exception while writing to " + f + " : ", e);
		}
	}
}