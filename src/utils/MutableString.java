package utils;

import java.io.Serializable;

/**
 * Mutable realization of {@link String}, based on {@link StringBuilder} with
 * additional <code>append</code> methods.
 * 
 * @author Mir4ik
 * @version 0.1 10.04.2015
 */
public class MutableString implements Appendable, Serializable, CharSequence {

	private static final long serialVersionUID = 4548654371023890563L;
	
	/**
	 * public to provide all methods 
	 */
	// TODO: rewrite with encapsulation?
	public final StringBuilder sb;
	
	/**
	 * Constructs an empty string with 16 as initial capacity.
	 */
	public MutableString() {
		sb = new StringBuilder();
	}
	
	/**
	 * Constructs an empty string with specified initial capacity.
	 * 
	 * @param capacity	initial capacity
	 */
	public MutableString(int capacity) {
		if (capacity < 0) {
			throw new IndexOutOfBoundsException("Wrong capacity " + capacity);
		}
		sb = new StringBuilder(capacity);
	}
	
	/**
	 * Constructs string initialized with specified characters. The initial
	 * capacity of the string is 16 plus the length of the argument.
	 * 
	 * @param cs	initial content
	 */
	public MutableString(CharSequence cs) {
		sb = new StringBuilder(cs);
	}
	
	/**
	 * Constructs string and appends the specified sequences to it.
	 */
	public MutableString(CharSequence ... cs) {
		this();
		append(cs);
	}

	@Override
	public int length() {
		return sb.length();
	}

	@Override
	public char charAt(int index) {
		return sb.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return sb.subSequence(start, end);
	}

	@Override
	public MutableString append(CharSequence csq) {
		sb.append(csq);
		return this;
	}

	@Override
	public MutableString append(CharSequence csq, int start, int end) {
		sb.append(csq, start, end);
		return this;
	}

	@Override
	public MutableString append(char c) {
		sb.append(c);
		return this;
	}
	
	/**
	 * Appends the specified sequences to this string.
	 * 
	 * @param cs	sequences to append
	 * @return		reference to this string
	 */
	public MutableString append(CharSequence ... cs) {
		for (CharSequence sequence : cs) {
			sb.append(sequence);
		}
		return this;
	}
	
	/**
	 * Appends string with length <code>count</code>, created from specified
	 * char, to this string.
	 * 
	 * @param c			char to form appended string
	 * @param count		length of appended string
	 * @return			reference to this string
	 */
	public MutableString append(char c, int count) {
		for (int i = 0; i < count; i++) {
			sb.append(c);
		}
		return this;
	}
	
	/**
	 * Method for deleting all characters from this string.
	 * 
	 * @return	reference to this string
	 */
	public MutableString clear() {
		sb.delete(0, sb.length());
		return this;
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
}