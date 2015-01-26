package bot.nlp;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Special realization of {@link ArrayList}, optimized for {@link String}
 * operations.
 * 
 * @author Mir4ik
 * @version 0.1 22.1.2015
 */
public class StringArrayList extends AbstractList<String> {

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private String[] elementData;

	private int size;
	
	public StringArrayList() {
		this(10);
	}
	
	public StringArrayList(int size) {
		if (size < 0) 
			throw new IndexOutOfBoundsException("Wrong size " + size);
		elementData = new String[size];
	}
	
	public StringArrayList(String str) {
		if (str != null) {
			elementData = str.split(" ");
			size = elementData.length;
		}
	}

	@Override
	public String get(int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException("Wrong index " + index);
		return elementData[index];
	}

	@Override
	public void add(int index, String element) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException("Wrong index " + index);
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

	@Override
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (elementData[i] == null) return i;
		} else {
			for (int i = 0; i < size; i++)
				if (((String) o).equalsIgnoreCase(elementData[i])) return i;
		}
		return -1;
	}

	@Override
	public boolean contains(Object o) {
		Iterator<String> it = iterator();
		if (o == null) {
			while (it.hasNext())
				if (it.next() == null) return true;
		} else {
			while (it.hasNext())
				if (((String) o).equalsIgnoreCase(it.next())) return true;
		}
		return false;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size - 1; i >= 0; i--)
				if (elementData[i] == null) return i;
		} else {
			for (int i = size - 1; i >= 0; i--)
				if (((String) o).equalsIgnoreCase(elementData[i])) return i;
		}
		return -1;
	}
}