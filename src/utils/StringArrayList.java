/*
 * StringArrayList.java	22.01.2015
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
package utils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Special realization of {@link ArrayList}, optimized for operations with
 * {@link String} data, for example it does not use 
 * {@link String#equals(Object) equals()} method, but 
 * {@link String#equalsIgnoreCase(String) equalsIgnoreCase()} instead.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.01.2015
 */
public class StringArrayList extends AbstractList<String> {

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private String[] elementData;

	private int size;
	
	/**
	 * Constructs an empty list with 10 as initial size.
	 */
	public StringArrayList() {
		this(10);
	}
	
	/**
	 * Constructs an empty list with specified initial size.
	 * 
	 * @param size	initial size
	 */
	public StringArrayList(int size) {
		if (size < 0) {
			throw new IndexOutOfBoundsException("Wrong size " + size);
		}
		elementData = new String[size];
	}
	
	/**
	 * Constructs string list initialized with specified characters, divided
	 * using whitespace character.
	 * 
	 * @param str	packed in string character data 
	 */
	public StringArrayList(String str) {
		if (str != null) {
			elementData = str.split(" ");
			size = elementData.length;
		}
	}

	@Override
	public String get(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("Wrong index " + index);
		}
		return elementData[index];
	}

	@Override
	public void add(int index, String element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("Wrong index " + index);
		}
		if (size + 1 - elementData.length > 0) {
			int oldCapacity = elementData.length;
			int newCapacity = oldCapacity + (oldCapacity >> 1);
			if (newCapacity - size + 1 < 0) newCapacity = size + 1;
			if (newCapacity - MAX_ARRAY_SIZE > 0) {
				if (size + 1 < 0) throw new OutOfMemoryError();
				newCapacity = (size + 1 > MAX_ARRAY_SIZE) ? 
						Integer.MAX_VALUE : MAX_ARRAY_SIZE;
			}
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
		System.arraycopy(elementData, index, elementData, index + 1,
				size - index);
		elementData[index] = element;
		size++;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns index of the first occurrence of {@link String}, that is equal
	 * ignore case to the specified, or -1 if list does not contain it.
	 * 
	 * @param o		string to search
	 * @return		index of the first occurrence if list contains equal ignore
	 * 				case string, or -1 otherwise
	 */
	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (((String) o).equalsIgnoreCase(elementData[i])) {
					return i;
				}
			}	
		}
		return -1;
	}

	/**
	 * Returns <code>true</code> if list contains {@link String}, that is equal
	 * ignore case to the specified.
	 * 
	 * @param o		string to search
	 * @return		<code>true</code> if list contains equal ignore case 
	 * 				string, or <code>false</code> otherwise
	 */
	@Override
	public boolean contains(Object o) {
		Iterator<String> it = iterator();
		if (o == null) {
			while (it.hasNext()) {
				if (it.next() == null) {
					return true;
				}
			}
		} else {
			while (it.hasNext()) {
				if (((String) o).equalsIgnoreCase(it.next())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns index of the last occurrence of {@link String}, that is equal
	 * ignore case to the specified, or -1 if list does not contain it.
	 * 
	 * @param o		string to search
	 * @return		index of the last occurrence if list contains equal ignore
	 * 				case string, or -1 otherwise
	 */
	@Override
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size - 1; i >= 0; i--) {
				if (elementData[i] == null) {
					return i;
				}
			}
		} else {
			for (int i = size - 1; i >= 0; i--) {
				if (((String) o).equalsIgnoreCase(elementData[i])) {
					return i;
				}
			}
		}
		return -1;
	}
}