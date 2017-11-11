package io.github.otameshi.fc;

import java.util.Set;
import java.util.stream.Stream;

import io.github.otameshi.fc.itr.FluentIterator;

public interface FluentSet<E> extends Set<E>, FluentCollection<E> {
	static <E> FluentSet<E> from(Set<E> set) {
		if (set instanceof FluentSet) {
			return (FluentSet<E>) set;
		}
		return WrapedFluentSet.from(set);
	}

	@SuppressWarnings("unchecked")
	static <E> FluentSet<E> empty() {
		return (FluentSet<E>) Internal.EMPTY_LIST;
	}

	@SafeVarargs
	static <E> FluentSet<E> of(E... values) {
		FluentList<E> iterable = FluentList.of(values);
		for (int i = 0; i < iterable.size(); i++) {
			E e = iterable.get(i);
			if (iterable.skip(i).contains(e)) {
				throw new IllegalArgumentException("duplicate element: " + e);
			}
		}

		return new AbstractFluentSet<E>() {

			@Override
			public FluentIterator<E> iterator() {
				return iterable.iterator();
			}

			@Override
			public int size() {
				return iterable.size();
			}
		};
	}

	@Override
	default Stream<E> stream() {
		return FluentCollection.super.stream();
	}
}