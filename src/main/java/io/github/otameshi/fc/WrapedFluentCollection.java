package io.github.otameshi.fc;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Predicate;

class WrapedFluentCollection<E> extends WrapedFluentIterable<E> implements FluentCollection<E> {
	private static class MarkSerializable<E> extends WrapedFluentCollection<E> implements java.io.Serializable {
		MarkSerializable(Collection<E> original) {
			super(original);
		}
	}

	static <E> WrapedFluentCollection<E> from(Collection<E> o) {
		if (o instanceof Serializable) {
			return new MarkSerializable<>(o);
		}
		return new WrapedFluentCollection<>(o);
	}

	private final Collection<E> original;

	protected WrapedFluentCollection(Collection<E> original) {
		super(original);
		this.original = original;
	}

	@Override
	public int size() {
		return original.size();
	}

	@Override
	public boolean isEmpty() {
		return original.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return original.contains(o);
	}

	@Override
	public Object[] toArray() {
		return original.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return original.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return original.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return original.remove(o);
	}

	@Override
	public void clear() {
		original.clear();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return original.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return original.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return original.retainAll(c);
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return original.removeIf(filter);
	}

	@Override
	public boolean equals(Object o) {
		return original.equals(o);
	}

	@Override
	public int hashCode() {
		return original.hashCode();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return original.containsAll(c);
	}

	@Override
	public String toString() {
		return original.toString();
	}
}