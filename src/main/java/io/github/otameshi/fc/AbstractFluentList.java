package io.github.otameshi.fc;

import java.util.AbstractList;

import io.github.otameshi.fc.itr.FluentIterator;
import io.github.otameshi.fc.itr.FluentListIterator;

public abstract class AbstractFluentList<E> extends AbstractList<E> implements FluentList<E> {

	@Override
	public FluentIterator<E> iterator() {
		return FluentIterator.from(super.iterator());
	}

	@Override
	public FluentListIterator<E> listIterator() {
		return FluentList.super.listIterator();
	}

	@Override
	public FluentListIterator<E> listIterator(int index) {
		return FluentListIterator.from(super.listIterator(index));
	}

	@Override
	public FluentList<E> subList(int fromIndex, int toIndex) {
		return FluentList.from(super.subList(fromIndex, toIndex));
	}
}