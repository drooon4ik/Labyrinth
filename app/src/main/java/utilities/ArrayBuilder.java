package utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ArrayBuilder<T> {
	private ArrayList<T> al;
	private Class clazz;
	private int width;
	private int row;

	public ArrayBuilder() {
		al = new ArrayList<T>();
		row = 0;
	}

	public void add(T number, int column) {
		al.add(row * width + column, number);
	}

	public void addRow() {
		if (row == 0) {
			width = al.size();
		}
		row++;
	}

	public T[][] getFormedArray() {
		clazz = al.toArray()[0].getClass();
		T[][] array = (T[][]) Array.newInstance(clazz, row, width);
		int count = 0;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < al.size() / row; j++) {
				array[i][j] = al.get(count);
				count++;
			}
		}

		return array;
	}

	@Override
	public String toString() {
		return "Rows = " + row + 
				"Width = " + width;
	}
}
