package io.github.otameshi.fc;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import io.github.otameshi.fc.itr.FluentListIterator;

class WrapedFluentList<E> extends WrapedFluentCollection<E> implements FluentList<E> {
	private static class MarkSerializable<E> extends WrapedFluentList<E> implements java.io.Serializable {
		MarkSerializable(List<E> original) {
			super(original);
		}
	}

	static <E> WrapedFluentList<E> from(List<E> o) {
		if (o instanceof Serializable) {
			return new MarkSerializable<>(o);
		}
		return new WrapedFluentList<>(o);
	}

	private final List<E> original;

	private WrapedFluentList(List<E> original) {
		super(original);
		this.original = original;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return original.addAll(index, c);
	}

	@Override
	public E get(int index) {
		return original.get(index);
	}

	@Override
	public E set(int index, E element) {
		return original.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		original.add(index, element);
	}

	@Override
	public E remove(int index) {
		return original.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return original.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return original.lastIndexOf(o);
	}

	@Override
	public FluentListIterator<E> listIterator(int index) {
		return FluentListIterator.from(original.listIterator(index));
	}

	@Override
	public FluentList<E> subList(int fromIndex, int toIndex) {
		return FluentList.from(original.subList(fromIndex, toIndex));
	}
}