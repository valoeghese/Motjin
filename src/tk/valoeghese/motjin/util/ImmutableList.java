package tk.valoeghese.motjin.util;

import java.util.Collection;
import java.util.function.Consumer;

public final class ImmutableList<T> {
	private ImmutableList(T[] array) {
		this.array = array;
		this.size = array.length;
	}

	private final T[] array;
	private final int size;

	public boolean contains(Object o) {
		for (T item : this.array) {
			if (item.equals(o)) {
				return true;
			}
		}

		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object item : c) {
			if (!this.contains(item)) {
				return false;
			}
		}

		return true;
	}

	public boolean isEmpty() {
		return this.array.length == 0;
	}

	public int size() {
		return this.size;
	}

	public T[] toArray() {
		return array;
	}

	public T get(int index) {
		return this.array[index];
	}

	public int indexOf(Object o) {
		int index = 0;

		for (; index < size; ++index) {
			if (array[index].equals(o)) {
				break;
			}
		}
		return index;
	}

	public int lastIndexOf(Object o) {
		int index = 0;

		for (int i = 0; i < size; ++i) {
			if (array[i].equals(o)) {
				index = i;
			}
		}
		return index;
	}

	public void forEach(Consumer<T> consumer) {
		for (T t : array) {
			consumer.accept(t);
		}
	}

	@SafeVarargs
	public static <T> ImmutableList<T> of(T...objects) {
		return new ImmutableList<T>(objects);
	}
}
