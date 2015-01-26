package bot.io;

import java.util.HashMap;
import java.util.Map;

import bot.core.ApplicationException;

/**
 * 
 * @author Mir4ik
 * @version 0.1 28.08.2014
 */
/*
 * TODO
 * 1. write load and save
 */
public class ReplacementDatabaseFacade {
	
	public static final String DATABASE_FOLDER = "Base";
	
	public static final char[] DIGITS = {'0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9'};
	
	public static final char[] CYRILLIC_ALPHABET = {'а', 'б', 'в', 'г', 'ґ',
		'д', 'е', 'є', 'ж', 'з', 'и', 'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о',
		'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ч', 'ц', 'ш', 'щ', 'ю', 'я'};
	
	public static final char[] LATIN_ALPHABET = {'a', 'b', 'c', 'd', 'e', 'f',
		'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z'};
	
	public static final int DATA_COUNT = CYRILLIC_ALPHABET.length + 
		LATIN_ALPHABET.length + DIGITS.length;
	
	@SuppressWarnings("unchecked")
	private final Map<String, String>[] replacements = new HashMap[DATA_COUNT];
	
	public String getReplacement(String key) throws ApplicationException {
		int index = foundIndex(key.charAt(0));
		if (replacements[index] == null) {
			replacements[index] = load(index);
			if (replacements[index] == null) {
				return null;
			}
		}
		return replacements[index].get(key);
	}
	
	public void setReplacement(String key, String value) 
		throws ApplicationException {
			int index = foundIndex(key.charAt(0));
			if (replacements[index] == null) {
				replacements[index] = load(index);
				if (replacements[index] == null) {
					replacements[index] = new HashMap<String, String>();
				}
			}
			replacements[index].put(key, value);
			save(replacements[index], index);
	}
	
	protected int foundIndex(Character c) {
		int index = 0;
		int digitsSize = DIGITS.length;
		int cyrillicsSize = CYRILLIC_ALPHABET.length;
		if (Character.isDigit(c)) {
			index = Integer.valueOf(c.toString());
		} else {
			for (int i = 0; i < cyrillicsSize; i++) {
				if (CYRILLIC_ALPHABET[i] == c || 
					CYRILLIC_ALPHABET[i] == Character.toLowerCase(c)) {
						index = i + digitsSize + 1;
				}
			}
			for (int i = 0; i < LATIN_ALPHABET.length; i++) {
				if (LATIN_ALPHABET[i] == c || 
					LATIN_ALPHABET[i] == Character.toLowerCase(c)) {
						index = i + digitsSize + cyrillicsSize + 2;
				}
			}
		}
		return index;
	}
	
	protected Map<String, String> load(int index) throws ApplicationException {
		
		return null;
	}
	
	protected void save(Map<String, String> map, int index) 
		throws ApplicationException {
		
	}
}